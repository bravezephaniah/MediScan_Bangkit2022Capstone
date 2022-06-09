package capstoneproject.mediscan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import capstoneproject.mediscan.databinding.ActivityChangeEmailBinding

class ChangeEmailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChangeEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}