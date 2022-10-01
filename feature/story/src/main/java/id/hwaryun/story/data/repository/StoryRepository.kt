package id.hwaryun.story.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.hwaryun.core.base.BaseResponse
import id.hwaryun.core.wrapper.DataResource
import id.hwaryun.shared.data.repository.Repository
import id.hwaryun.story.data.database.StoryDatabase
import id.hwaryun.story.data.model.entity.StoryEntity
import id.hwaryun.story.data.model.response.StoryResponse
import id.hwaryun.story.data.network.datasource.StoryDataSource
import id.hwaryun.story.data.network.datasource.StoryRemoteMediator
import id.hwaryun.story.data.network.service.StoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import javax.inject.Inject

typealias PostDataResource = DataResource<BaseResponse<Unit>>
typealias StoryDataResource = DataResource<BaseResponse<StoryResponse>>
typealias StoryPagingDataResource = PagingData<StoryEntity>

interface StoryRepository {
    suspend fun addNewStory(body: RequestBody): Flow<PostDataResource>
    suspend fun getStoriesWithLocation(): Flow<StoryDataResource>
    suspend fun getStories(): Flow<StoryPagingDataResource>
}

class StoryRepositoryImpl @Inject constructor(
    private val dataSource: StoryDataSource,
    private val api: StoryService,
    private val database: StoryDatabase
) : StoryRepository, Repository() {

    override suspend fun addNewStory(body: RequestBody): Flow<PostDataResource> = flow {
        emit(safeNetworkCall { dataSource.addNewStory(body) })
    }

    override suspend fun getStoriesWithLocation(): Flow<StoryDataResource> = flow {
        emit(safeNetworkCall { dataSource.getStoriesWithLocation() })
    }

    override suspend fun getStories(): Flow<StoryPagingDataResource> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = StoryRemoteMediator(database, api),
            pagingSourceFactory = {
                database.storyDao().getStories()
            }
        ).flow
    }
}