package capstoneproject.mediscan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import capstoneproject.mediscan.R
import capstoneproject.mediscan.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toLoginButton.setOnClickListener {startActivity(Intent(this, LoginActivity::class.java))}
        binding.toSignupButton.setOnClickListener {startActivity(Intent(this, SignupActivity::class.java))}
    }
}