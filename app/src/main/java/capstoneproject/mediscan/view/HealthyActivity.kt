package capstoneproject.mediscan.view

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import capstoneproject.mediscan.databinding.ActivityHealthyBinding
import com.bumptech.glide.Glide

class HealthyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHealthyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val data = intent.getParcelableExtra("IMAGE") as Bitmap?

        Glide.with(this)
            .load(data)
            .into(binding.resultImage)

        binding.userLogo.setOnClickListener {
            val intent = Intent(this@HealthyActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}