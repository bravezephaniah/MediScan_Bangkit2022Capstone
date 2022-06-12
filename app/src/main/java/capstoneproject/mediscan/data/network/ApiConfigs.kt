package capstoneproject.mediscan.data.network

import androidx.viewbinding.BuildConfig
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("users")
    fun registerUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @PUT("users")
    fun updateUser(
        @Header("Authorization") authorization: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UpdateResponse>

    @Multipart
    @POST("history")
    fun uploadHistory(
        @Header("Authorization") authorization: String,
        @Part("result") result: String,
        @Part file: MultipartBody.Part,
    ): Call<UploadHistoryResponse>

    @GET("history")
    fun getHistory(
        @Header("Authorization") authorization: String
    ): Call<List<GetHistoryResponseItem>>

    @DELETE("history/{id}")
    fun deleteHistory(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Call<DeleteHistoryResponse>
}

class ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor =
//            if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY)
//        } else {
//            HttpLoggingInterceptor().setLevel(
//                HttpLoggingInterceptor.Level.NONE)
//        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.101.255.122:8181/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}