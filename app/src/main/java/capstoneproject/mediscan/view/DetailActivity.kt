package capstoneproject.mediscan.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import capstoneproject.mediscan.R
import capstoneproject.mediscan.data.MainViewModel
import capstoneproject.mediscan.data.ViewModelFactory
import capstoneproject.mediscan.data.local.UserPreferences
import capstoneproject.mediscan.databinding.ActivityDetailBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val viewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]

        binding.buttonLogout.setOnClickListener{
            viewModel.deleteToken()
            val intent = Intent(this@DetailActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}