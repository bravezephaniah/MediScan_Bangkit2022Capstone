package capstoneproject.mediscan.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import capstoneproject.mediscan.databinding.ActivitySickBinding
import com.bumptech.glide.Glide

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SickActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySickBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySickBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val isResult = intent.getBooleanExtra("ISRESULT", false)
        val id = intent.getIntExtra("ID", 0)
        if(isResult){
            val dataResult = intent.getStringExtra("IMAGE_RESULT")
            Glide.with(this)
                .load(dataResult)
                .into(binding.resultImage)

            binding.buttonDeleteHistory.visibility = View.VISIBLE
        }else{
            val data = intent.getParcelableExtra("IMAGE") as Bitmap?
            Glide.with(this)
                .load(data)
                .into(binding.resultImage)
        }

        binding.hospitalBtnOther.setOnClickListener{
            val gmmIntentUri = Uri.parse(getString(R.string.URI_nearest_hospital))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.userLogo.setOnClickListener {
            val intent = Intent(this@SickActivity, DetailActivity::class.java)
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