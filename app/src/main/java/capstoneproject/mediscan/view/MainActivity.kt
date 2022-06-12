package capstoneproject.mediscan.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import capstoneproject.mediscan.R
import capstoneproject.mediscan.data.MainViewModel
import capstoneproject.mediscan.data.ViewModelFactory
import capstoneproject.mediscan.data.local.UserPreferences
import capstoneproject.mediscan.databinding.ActivityMainBinding
import capstoneproject.mediscan.helper.reduceImageFile
import capstoneproject.mediscan.helper.rotateBitmap
import capstoneproject.mediscan.helper.uriToFile
import capstoneproject.mediscan.ml.ConvertedModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var image: Bitmap
    private var isImageChosen = false
    private var getFile: File? = null
    private lateinit var analyzeResult: String
    private lateinit var viewModel: MainViewModel

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            image = result

            binding.imgviewCaptured.setImageBitmap(result)

            isImageChosen = true
        }
    }
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)

            getFile = myFile

            image = BitmapFactory.decodeFile(myFile.path)

            binding.imgviewCaptured.setImageURI(selectedImg)

            isImageChosen = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreferences.getInstance(dataStore)))[MainViewModel::class.java]

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.buttonToCamera.setOnClickListener { startCameraX() }
        binding.buttonToGallery.setOnClickListener { startGallery() }
        binding.userLogo.setOnClickListener {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            startActivity(intent)
        }
        binding.buttonToAnalyze.setOnClickListener {
            if (!isImageChosen) {
                Toast.makeText(this, getString(R.string.image_null), Toast.LENGTH_SHORT).show()
            } else {
                analyzeResult = analyzeImage(image)
                uploadStory()
                when (analyzeResult) {
                    "0" -> {
                        val intent = Intent(this@MainActivity, HealthyActivity::class.java)
                        intent.putExtra("IMAGE", image)
                        startActivity(intent)
                        finish()
                    }
                    "1" -> {
                        val intent = Intent(this@MainActivity, CancerActivity::class.java)
                        intent.putExtra("IMAGE", image)
                        startActivity(intent)
                        finish()
                    }
                    "2" -> {
                        val intent = Intent(this@MainActivity, SickActivity::class.java)
                        intent.putExtra("IMAGE", image)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        cameraLauncher.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"

        val chooser = Intent.createChooser(intent, "Select a Picture")
        galleryLauncher.launch(chooser)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.permission_denied),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun analyzeImage(bitmap: Bitmap): String {
        val model = ConvertedModel.newInstance(this)

        val inputFeature0 =
            TensorBuffer.createFixedSize(intArrayOf(1, 200, 150, 3), DataType.FLOAT32)
        image = Bitmap.createScaledBitmap(bitmap, 400, 300, true)
        inputFeature0.loadBuffer(TensorImage.fromBitmap(image).buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val outputResult = getMax(outputFeature0.floatArray).toString()

        model.close()

        return outputResult
    }

    private fun getMax(arr: FloatArray): Int {
        var index = 0
        var min = 0.0f

        for (i in 0..2) {
            if (arr[i] > min) {
                index = i
                min = arr[i]
            }
        }

        return index
    }

    private fun uploadStory() {
        if (getFile != null) {
            val file = (reduceImageFile(getFile as File))
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val historyMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )

            viewModel.getToken().observe(this) {
                viewModel.uploadHistory(it, analyzeResult, historyMultipart)
            }
        } else {
            Toast.makeText(this, getString(R.string.image_null), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}