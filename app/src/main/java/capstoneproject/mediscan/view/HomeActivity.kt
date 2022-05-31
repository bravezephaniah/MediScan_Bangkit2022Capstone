package capstoneproject.mediscan.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import capstoneproject.mediscan.R
import capstoneproject.mediscan.data.MainViewModel
import capstoneproject.mediscan.data.ViewModelFactory
import capstoneproject.mediscan.data.local.UserPreferences
import capstoneproject.mediscan.databinding.ActivityHomeBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]
        val isJustLogin = intent.getBooleanExtra(LOGIN_FLAG, false)

        if(!isJustLogin){
            viewModel.getToken().observe(this){
                if(it.isEmpty()){
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }

        binding.buttonStart.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    companion object{
        const val LOGIN_FLAG = "LOGIN_FLAG"
    }

}