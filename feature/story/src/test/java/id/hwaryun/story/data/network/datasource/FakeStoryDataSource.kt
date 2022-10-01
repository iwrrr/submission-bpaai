package id.hwaryun.story.data.network.datasource

import id.hwaryun.core.base.BaseResponse
import id.hwaryun.story.data.model.response.StoryResponse
import id.hwaryun.story.utils.StoryDummy
import okhttp3.RequestBody

class FakeStoryDataSource : StoryDataSource {

    override suspend fun addNewStory(body: RequestBody): BaseResponse<Unit> {
        return BaseResponse(false, null, null, null)
    }

    override suspend fun getStoriesWithLocation(): BaseResponse<StoryResponse> {
        return StoryDummy.getStories()
    }

    override suspend fun getStories(): BaseResponse<StoryResponse> {
        return StoryDummy.getStories()
    }
}