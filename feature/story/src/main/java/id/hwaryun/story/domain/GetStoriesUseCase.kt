package id.hwaryun.story.domain

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import id.hwaryun.core.base.BaseUseCase
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.story.data.model.viewparam.StoryViewParam
import id.hwaryun.story.data.repository.StoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias StoryResult = PagingData<StoryViewParam>?

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<CoroutineScope, StoryResult>(dispatcher) {

    override suspend fun execute(param: CoroutineScope?): Flow<ViewResource<StoryResult>> = flow {
        emit(ViewResource.Loading())
        param?.let {
            repository.getStories().cachedIn(param).collect { storyResult ->
                emit(ViewResource.Success(storyResult.map { it.toViewParam() }))
            }
        }
    }
}