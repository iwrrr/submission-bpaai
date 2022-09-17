package id.hwaryun.story.data.model.viewparam

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryViewParam(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double,
    val long: Double
) : Parcelable