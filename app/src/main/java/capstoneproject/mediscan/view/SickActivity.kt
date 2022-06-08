package capstoneproject.mediscan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import capstoneproject.mediscan.databinding.ActivitySickBinding

class SickActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySickBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySickBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.hospitalBtn.setOnClickListener{
            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
        }
        binding.userLogo.setOnClickListener {
            val intent = Intent(this@SickActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}