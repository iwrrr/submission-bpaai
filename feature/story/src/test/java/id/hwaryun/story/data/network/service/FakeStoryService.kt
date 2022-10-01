package id.hwaryun.story.data.network.service

import id.hwaryun.core.base.BaseResponse
import id.hwaryun.story.data.model.response.StoryResponse
import id.hwaryun.story.utils.StoryDummy
import okhttp3.RequestBody

class FakeStoryService : StoryService {

    override suspend fun addNewStory(body: RequestBody): BaseResponse<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getStories(
        page: Int,
        size: Int,
        location: Int
    ): BaseResponse<StoryResponse> {
        return BaseResponse(
            error = false,
            message = "Stories fetched successfully",
            user = null,
            stories = StoryDummy.storyResponse()
                .subList((page - 1) * size, (page - 1) * size + size)
        )
    }
}