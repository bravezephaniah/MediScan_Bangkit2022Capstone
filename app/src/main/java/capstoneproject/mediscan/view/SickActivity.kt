package capstoneproject.mediscan.view

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import capstoneproject.mediscan.R
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

        binding.hospitalBtnOther.setOnClickListener{
            val gmmIntentUri = Uri.parse(getString(R.string.URI_nearest_hospital))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.userLogo.setOnClickListener {
            val intent = Intent(this@SickActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}