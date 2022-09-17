package id.hwaryun.story.data.network.datasource

import id.hwaryun.core.base.BaseResponse
import id.hwaryun.story.data.model.response.StoryResponse
import id.hwaryun.story.data.network.service.StoryService
import okhttp3.RequestBody
import javax.inject.Inject

interface StoryDataSource {
    suspend fun addNewStory(body: RequestBody): BaseResponse<Unit>
    suspend fun getStories(): BaseResponse<StoryResponse>
}

class StoryDataSourceImpl @Inject constructor(
    private val api: StoryService
) : StoryDataSource {

    override suspend fun addNewStory(body: RequestBody): BaseResponse<Unit> {
        return api.addNewStory(body)
    }

    override suspend fun getStories(): BaseResponse<StoryResponse> {
        return api.getStories()
    }
}