package id.hwaryun.story.data.repository

import id.hwaryun.core.base.BaseResponse
import id.hwaryun.core.wrapper.DataResource
import id.hwaryun.shared.data.repository.Repository
import id.hwaryun.story.data.model.response.StoryResponse
import id.hwaryun.story.data.network.datasource.StoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import javax.inject.Inject

typealias PostDataResource = DataResource<BaseResponse<Unit>>
typealias StoryDataResource = DataResource<BaseResponse<StoryResponse>>

interface StoryRepository {
    suspend fun addNewStory(body: RequestBody): Flow<PostDataResource>
    suspend fun getStories(): Flow<StoryDataResource>
}

class StoryRepositoryImpl @Inject constructor(
    private val dataSource: StoryDataSource
) : StoryRepository, Repository() {

    override suspend fun addNewStory(body: RequestBody): Flow<PostDataResource> = flow {
        emit(safeNetworkCall { dataSource.addNewStory(body) })
    }

    override suspend fun getStories(): Flow<StoryDataResource> = flow {
        emit(safeNetworkCall { dataSource.getStories() })
    }
}