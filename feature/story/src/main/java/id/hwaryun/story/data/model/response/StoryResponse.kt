package id.hwaryun.story.data.model.response

import com.google.gson.annotations.SerializedName
import id.hwaryun.story.data.model.entity.StoryEntity
import id.hwaryun.story.data.model.viewparam.StoryViewParam

data class StoryResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("photoUrl")
    val photoUrl: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val long: Double?,
) {

    fun toViewParam(): StoryViewParam = StoryViewParam(
        id = id.orEmpty(),
        name = name.orEmpty(),
        description = description.orEmpty(),
        photoUrl = photoUrl.orEmpty(),
        createdAt = createdAt.orEmpty(),
        lat = lat ?: 0.0,
        long = long ?: 0.0
    )

    fun toEntity(): StoryEntity = StoryEntity(
        id = id.orEmpty(),
        name = name.orEmpty(),
        description = description.orEmpty(),
        photoUrl = photoUrl.orEmpty(),
        createdAt = createdAt.orEmpty(),
        lat = lat ?: 0.0,
        long = long ?: 0.0
    )
}