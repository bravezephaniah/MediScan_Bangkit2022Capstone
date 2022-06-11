package capstoneproject.mediscan.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import capstoneproject.mediscan.R
import capstoneproject.mediscan.databinding.ActivityCancerBinding
import capstoneproject.mediscan.databinding.ActivityHealthyBinding

class HealthyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHealthyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.userLogo.setOnClickListener {
            val intent = Intent(this@HealthyActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}