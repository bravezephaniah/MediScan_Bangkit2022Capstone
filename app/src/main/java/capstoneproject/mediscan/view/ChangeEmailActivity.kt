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
import capstoneproject.mediscan.databinding.ActivityChangeEmailBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class ChangeEmailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChangeEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]

        var username = ""
        viewModel.getUsername().observe(this){
            username = it
        }

        binding.changeEmailButton.setOnClickListener {
            val oldEmail = binding.emailOldEdittext.text.toString()
            val newEmail = binding.emailNewEdittext.text.toString()
            val password = binding.passwordChangeemailEdittext.text.toString()

            if(oldEmail == newEmail){
                Toast.makeText(this, getString(R.string.email_change_fail), Toast.LENGTH_SHORT).show()
                binding.emailOldEdittext.text?.clear()
                binding.emailNewEdittext.text?.clear()
                binding.passwordChangeemailEdittext.text?.clear()
            }else{
                viewModel.getToken().observe(this){ viewModel.updateUser("Bearer $it", username, newEmail, password) }
                viewModel.saveEmail(newEmail)
                viewModel.updateResponse.observe(this){
                    if(it.status == "success"){
                        val intent = Intent(this, DetailActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra(HomeActivity.LOGIN_FLAG, true)

                        startActivity(intent)
                        finish()
                    }else if(it.status == "fail"){
                        Toast.makeText(this, getString(R.string.email_change_fail), Toast.LENGTH_SHORT).show()
                        binding.emailOldEdittext.text?.clear()
                        binding.emailNewEdittext.text?.clear()
                        binding.passwordChangeemailEdittext.text?.clear()
                    }
                }
            }
        }
    }
}