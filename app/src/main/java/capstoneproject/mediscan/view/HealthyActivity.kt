package capstoneproject.mediscan.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import capstoneproject.mediscan.data.MainViewModel
import capstoneproject.mediscan.data.ViewModelFactory
import capstoneproject.mediscan.data.local.UserPreferences
import capstoneproject.mediscan.databinding.ActivityHealthyBinding
import com.bumptech.glide.Glide

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class HealthyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHealthyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthyBinding.inflate(layoutInflater)
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

        binding.userLogo.setOnClickListener {
            val intent = Intent(this@HealthyActivity, DetailActivity::class.java)
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