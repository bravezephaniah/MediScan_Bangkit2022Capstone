package capstoneproject.mediscan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import capstoneproject.mediscan.databinding.ActivityChangeUsernameBinding

class ChangeUsernameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChangeUsernameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}