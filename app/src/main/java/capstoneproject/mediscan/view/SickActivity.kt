package capstoneproject.mediscan.view

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import capstoneproject.mediscan.databinding.ActivitySickBinding
import com.bumptech.glide.Glide

class SickActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySickBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySickBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val data = intent.getParcelableExtra("IMAGE") as Bitmap?

        Glide.with(this)
            .load(data)
            .into(binding.resultImage)

        binding.hospitalBtn.setOnClickListener{
            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
        }
        binding.userLogo.setOnClickListener {
            val intent = Intent(this@SickActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}