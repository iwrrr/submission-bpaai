package id.hwaryun.core.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("loginResult")
    val user: T?,
    @SerializedName("listStory")
    val stories: List<T>?,
)
