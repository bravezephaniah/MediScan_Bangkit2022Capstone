package capstoneproject.mediscan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import capstoneproject.mediscan.R
import capstoneproject.mediscan.databinding.ActivityCancerBinding
import capstoneproject.mediscan.databinding.ActivitySickBinding

class CancerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCancerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCancerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hospitalBtn.setOnClickListener{
            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
        }
        binding.userLogo.setOnClickListener {
            val intent = Intent(this@CancerActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}