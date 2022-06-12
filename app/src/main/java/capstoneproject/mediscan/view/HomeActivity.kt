package capstoneproject.mediscan.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import capstoneproject.mediscan.data.MainViewModel
import capstoneproject.mediscan.data.ViewModelFactory
import capstoneproject.mediscan.data.local.UserPreferences
import capstoneproject.mediscan.data.network.GetHistoryResponseItem
import capstoneproject.mediscan.databinding.ActivityHomeBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]
        val isJustLogin = intent.getBooleanExtra(LOGIN_FLAG, false)

        viewModel.getToken().observe(this){
            viewModel.getHistory("Bearer $it")
        }

        viewModel.getHistoryResponse.observe(this){
            if (it != null) {
                setSearchResult(it)
            }
        }

        if (!isJustLogin) {
            viewModel.getToken().observe(this) {
                if (it.isEmpty()) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }

        binding.buttonStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.userLogo.setOnClickListener {
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setSearchResult(listHistory: List<GetHistoryResponseItem?>?) {
        val layoutManager = GridLayoutManager(this, 3)
        binding.rvHistory.layoutManager = layoutManager

        val historyAdapter = HistoryAdapter(listHistory)
        binding.rvHistory.adapter = historyAdapter

        historyAdapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GetHistoryResponseItem?) {
                Toast.makeText(this@HomeActivity, "${data?.result}", Toast.LENGTH_SHORT).show()
                when (data?.result) {
                    "\"0\""  -> {
                        val intent = Intent(this@HomeActivity, HealthyActivity::class.java)
                        intent.putExtra("IMAGE_RESULT", data.imgUrl)
                        intent.putExtra("ISRESULT", true)
                        intent.putExtra("ID", data.id)
                        Log.d("HomeActivity", "onItemClicked: ${data.imgUrl}, ${data.id}")
                        startActivity(intent)
                        finish()
                    }
                    "\"1\"" -> {
                        val intent = Intent(this@HomeActivity, CancerActivity::class.java)
                        intent.putExtra("IMAGE_RESULT", data.imgUrl)
                        intent.putExtra("ISRESULT", true)
                        intent.putExtra("ID", data.id)
                        Log.d("HomeActivity", "onItemClicked: ${data.imgUrl}, ${data.id}")
                        startActivity(intent)
                        finish()
                    }
                    "\"2\""  -> {
                        val intent = Intent(this@HomeActivity, SickActivity::class.java)
                        intent.putExtra("IMAGE_RESULT", data.imgUrl)
                        intent.putExtra("ISRESULT", true)
                        intent.putExtra("ID", data.id)
                        Log.d("HomeActivity", "onItemClicked: ${data.imgUrl}, ${data.id}")
                        startActivity(intent)
                        finish()
                    }
                }
            }

        })
    }

    companion object {
        const val LOGIN_FLAG = "LOGIN_FLAG"
    }

}