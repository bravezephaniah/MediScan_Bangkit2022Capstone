package capstoneproject.mediscan.view

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import capstoneproject.mediscan.R
import capstoneproject.mediscan.databinding.ActivityCancerBinding
import com.bumptech.glide.Glide

class CancerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCancerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val data = intent.getParcelableExtra("IMAGE") as Bitmap?

        Glide.with(this)
            .load(data)
            .into(binding.resultImage)

        binding.hospitalBtn.setOnClickListener{
            val gmmIntentUri = Uri.parse(getString(R.string.URI_nearest_hospital))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.userLogo.setOnClickListener {
            val intent = Intent(this@CancerActivity, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}