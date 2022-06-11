package capstoneproject.mediscan.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import capstoneproject.mediscan.data.MainViewModel
import capstoneproject.mediscan.data.ViewModelFactory
import capstoneproject.mediscan.data.local.UserPreferences
import capstoneproject.mediscan.databinding.ActivityDetailBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]

        binding.buttonLogout.setOnClickListener{
            viewModel.deleteToken()
            viewModel.deleteUsername()
            viewModel.deleteEmail()

            val intent = Intent(this@DetailActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            startActivity(intent)
            finish()
        }
        binding.detailUsername.setOnClickListener{
            startActivity(Intent(this, ChangeUsernameActivity::class.java))
        }
        binding.detailEmail.setOnClickListener{
            startActivity(Intent(this, ChangeEmailActivity::class.java))
        }

        viewModel.getUsername().observe(this){
            binding.detailUsername.text = it
        }
        viewModel.getEmail().observe(this){
            binding.detailEmail.text = it
        }
    }
}