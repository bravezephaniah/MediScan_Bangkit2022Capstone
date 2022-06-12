package capstoneproject.mediscan.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import capstoneproject.mediscan.R
import capstoneproject.mediscan.data.MainViewModel
import capstoneproject.mediscan.data.ViewModelFactory
import capstoneproject.mediscan.data.local.UserPreferences
import capstoneproject.mediscan.databinding.ActivityCancerBinding
import com.bumptech.glide.Glide
import kotlin.math.log

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class CancerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCancerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val isResult = intent.getBooleanExtra("ISRESULT", false)
        val id = intent.getIntExtra("ID", 0)
        Log.d("CancerActivity", "onCreate: $isResult, $id")
        if(isResult){
            val dataResult = intent.getStringExtra("IMAGE_RESULT")
            Glide.with(this)
                .load(dataResult)
                .into(binding.resultImage)

            Log.d("CancerActivity", "onCreate: $dataResult")

            binding.buttonDeleteHistory.visibility = View.VISIBLE
        }else{
            val data = intent.getParcelableExtra("IMAGE") as Bitmap?
            Glide.with(this)
                .load(data)
                .into(binding.resultImage)
        }

        binding.hospitalBtnCancer.setOnClickListener{
            val gmmIntentUri = Uri.parse(getString(R.string.URI_nearest_hospital))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.userLogo.setOnClickListener {
            val intent = Intent(this@CancerActivity, DetailActivity::class.java)
            startActivity(intent)
        }
        binding.buttonDeleteHistory.setOnClickListener {
            val viewModel = ViewModelProvider(this,
                ViewModelFactory(UserPreferences.getInstance(dataStore))
            )[MainViewModel::class.java]

            viewModel.getToken().observe(this){ token ->
                viewModel.deleteHistory(token, id.toString())
            }

            viewModel.deleteHistoryResponse.observe(this){
                if(it.status == "success"){
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this, "Something is wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}