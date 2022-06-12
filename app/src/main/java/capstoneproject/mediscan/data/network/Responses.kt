package capstoneproject.mediscan.data.network

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null,
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
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)

data class UpdateResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class GetHistoryResponse(

    @field:SerializedName("GetHistoryResponse")
    val getHistoryResponse: List<GetHistoryResponseItem?>? = null
)

data class GetHistoryResponseItem(

    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("img_url")
    val imgUrl: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class UploadHistoryResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class DeleteHistoryResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
