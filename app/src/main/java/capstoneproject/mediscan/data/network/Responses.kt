package capstoneproject.mediscan.data.network

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class LoginResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("accessToken")
    val accessToken: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)