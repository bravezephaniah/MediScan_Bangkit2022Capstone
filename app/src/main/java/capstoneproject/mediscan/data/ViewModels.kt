package capstoneproject.mediscan.data

import android.util.Log
import androidx.lifecycle.*
import capstoneproject.mediscan.data.local.UserPreferences
import capstoneproject.mediscan.data.network.ApiConfig
import capstoneproject.mediscan.data.network.LoginResponse
import capstoneproject.mediscan.data.network.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreferences): ViewModel() {
    
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private var _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private var _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun getToken(): LiveData<String>{
        return pref.getToken().asLiveData()
    }

    fun getUsername(): LiveData<String>{
        return pref.getUsername().asLiveData()
    }

    fun getEmail(): LiveData<String>{
        return pref.getEmail().asLiveData()
    }

    fun saveToken(token: String){
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

    fun saveUsername(username: String){
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }

    fun saveEmail(email: String){
        viewModelScope.launch {
            pref.saveEmail(email)
        }
    }

    fun deleteToken(){
        viewModelScope.launch{
            pref.deleteToken()
        }
    }

    fun deleteUsername(){
        viewModelScope.launch{
            pref.deleteUsername()
        }
    }

    fun deleteEmail(){
        viewModelScope.launch{
            pref.deleteEmail()
        }
    }

    fun registerUser(username: String, email: String, password: String){
        _isLoading.value = true
        val client = ApiConfig().getApiService().registerUser(username, email, password)
        client.enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _registerResponse.value = response.body()
                    Log.d(TAG, "onResponseSuccess: ${response.body()?.status} ${response.body()?.message}")
                }else{
                    Log.e(TAG, "onResponseFailure: ${response.body()?.status} ${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun loginUser(username: String, password: String){
        _isLoading.value = true
        val client = ApiConfig().getApiService().loginUser(username, password)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _loginResponse.value = response.body()
                    _isLoggedIn.value = true
                    response.body()?.accessToken?.let { saveToken(it) }
                    response.body()?.username?.let { saveUsername(it) }
                    response.body()?.email?.let { saveEmail(it) }
                    Log.d(TAG, "onResponseSuccess: Login Success")
                }else{
                    _isLoggedIn.value = false
                    Log.e(TAG, "onResponseFailure: Login Failed")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isLoggedIn.value = false
                Log.e("MainViewModel", "onFailure: ${t.message}")
            }

        })
    }

    companion object{
        const val TAG = "MainViewModel"
    }
}

class ViewModelFactory(private val pref: UserPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: "+ modelClass.name)
    }
}