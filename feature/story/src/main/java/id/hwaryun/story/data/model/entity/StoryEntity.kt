package id.hwaryun.story.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.hwaryun.story.data.model.viewparam.StoryViewParam

@Entity(tableName = "stories")
data class StoryEntity(
    @PrimaryKey
    var id: String,
    var name: String? = null,
    var description: String? = null,
    var photoUrl: String? = null,
    var createdAt: String? = null,
    var lat: Double? = null,
    var long: Double? = null,
) {

    constructor() : this("")

    fun toViewParam(): StoryViewParam = StoryViewParam(
        id = id,
        name = name.orEmpty(),
        description = description.orEmpty(),
        photoUrl = photoUrl.orEmpty(),
        createdAt = createdAt.orEmpty(),
        lat = lat ?: 0.0,
        long = long ?: 0.0
    )
}