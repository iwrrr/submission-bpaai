package id.hwaryun.storygram.auth.data.model.response

import com.google.gson.annotations.SerializedName
import id.hwaryun.storygram.auth.data.model.viewparam.UserViewParam

data class UserResponse(
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("token")
    val token: String?,
) {

    fun toViewParam(): UserViewParam = UserViewParam(
        userId = userId.orEmpty(),
        name = name.orEmpty(),
        token = token.orEmpty(),
    )
}