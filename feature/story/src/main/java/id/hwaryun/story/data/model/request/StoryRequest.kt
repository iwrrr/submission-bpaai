package id.hwaryun.story.data.model.request

import com.google.gson.annotations.SerializedName

data class StoryRequest(
    @SerializedName("description")
    val description: String?,
//    @SerializedName("photo")
//    val photo: MultipartBody.Part?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("long")
    val long: Double?,
)
