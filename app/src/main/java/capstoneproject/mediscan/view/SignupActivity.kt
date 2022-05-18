package capstoneproject.mediscan.view

import android.content.Context
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
import capstoneproject.mediscan.databinding.ActivitySignupBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreferences.getInstance(dataStore)))[MainViewModel::class.java]

        viewModel.isLoading.observe(this){showLoading(it)}

        binding.signupButton.setOnClickListener {
            val username = binding.usernameSignupEdittext.text.toString()
            val email = binding.emailSignupEdittext.text.toString()
            val password = binding.passwordSignupEdittext.text.toString()

            viewModel.registerUser(username, email, password)
            viewModel.isRegistered.observe(this){
                if(it){
                    Toast.makeText(this, getString(R.string.regist_success), Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this, getString(R.string.regist_fail), Toast.LENGTH_SHORT).show()
                    binding.usernameSignupEdittext.text?.clear()
                    binding.emailSignupEdittext.text?.clear()
                    binding.passwordSignupEdittext.text?.clear()
                }
            }
        }

    }

    private fun showLoading(b: Boolean) {
        if (b) {
            binding.loadingSignup.visibility = View.VISIBLE
        } else {
            binding.loadingSignup.visibility = View.GONE
        }
    }
}