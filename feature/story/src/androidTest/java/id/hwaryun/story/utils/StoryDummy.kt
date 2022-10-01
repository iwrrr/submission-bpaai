package id.hwaryun.story.utils

import id.hwaryun.core.base.BaseResponse
import id.hwaryun.story.data.model.response.StoryResponse

object StoryDummy {

    fun storyResponse(): MutableList<StoryResponse> {
        val items = mutableListOf<StoryResponse>()

        for (i in 0..5) {
            val story = StoryResponse(
                id = "story-FvU4u0Vp2S3PMsFg",
                name = "user",
                description = "Lorem Ipsum",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                createdAt = "2022-01-08T06:34:18.598Z",
                lat = 0.0,
                long = 0.0
            )
            items.add(story)
        }

        return items
    }

    fun getStories() = BaseResponse(
        error = false,
        message = "Stories fetched successfully",
        user = null,
        stories = storyResponse()
    )
}