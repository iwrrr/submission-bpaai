package id.hwaryun.story.domain

import com.catnip.core.base.BaseUseCase
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.utils.suspendSubscribe
import id.hwaryun.story.data.model.viewparam.StoryViewParam
import id.hwaryun.story.data.repository.StoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias StoryResult = List<StoryViewParam>?

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Nothing, StoryResult>(dispatcher) {

    override suspend fun execute(param: Nothing?): Flow<ViewResource<StoryResult>> = flow {
        emit(ViewResource.Loading())
        repository.getStories().collect { storyResult ->
            storyResult.suspendSubscribe(
                doOnSuccess = {
                    val stories = storyResult.payload?.stories?.map { it.toViewParam() }
                    if (stories.isNullOrEmpty()) {
                        emit(ViewResource.Empty())
                    } else {
                        emit(ViewResource.Success(stories))
                    }
                },
                doOnError = { error ->
                    emit(ViewResource.Error(error.exception))
                }
            )
        }
    }
}