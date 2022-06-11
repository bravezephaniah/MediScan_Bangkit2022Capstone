package capstoneproject.mediscan.view

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import capstoneproject.mediscan.R
import capstoneproject.mediscan.data.MainViewModel
import capstoneproject.mediscan.data.ViewModelFactory
import capstoneproject.mediscan.data.local.UserPreferences
import capstoneproject.mediscan.databinding.ActivityChangeUsernameBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class ChangeUsernameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChangeUsernameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]

        var email = ""
        viewModel.getEmail().observe(this){
            email = it
        }

        binding.changeUsernameButton.setOnClickListener {
            val oldUsername = binding.usernameOldEdittext.text.toString()
            val newUsername = binding.usernameNewEdittext.text.toString()
            val password = binding.passwordChangeusernameEdittext.text.toString()

            if(oldUsername == newUsername){
                Toast.makeText(this, getString(R.string.fail_change_username), Toast.LENGTH_SHORT).show()
                binding.usernameOldEdittext.text?.clear()
                binding.usernameNewEdittext.text?.clear()
                binding.passwordChangeusernameEdittext.text?.clear()
            }else{
                viewModel.getToken().observe(this){ viewModel.updateUser("Bearer $it", newUsername, email, password) }
                viewModel.saveUsername(newUsername)
                viewModel.updateResponse.observe(this){
                    if(it.status == "success"){
                        val intent = Intent(this, DetailActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra(HomeActivity.LOGIN_FLAG, true)

                        startActivity(intent)
                        finish()
                    }else if(it.status == "fail"){
                        Toast.makeText(this, getString(R.string.fail_change_username), Toast.LENGTH_SHORT).show()
                        binding.usernameOldEdittext.text?.clear()
                        binding.usernameNewEdittext.text?.clear()
                        binding.passwordChangeusernameEdittext.text?.clear()
                    }
                }
            }
        }
    }
}