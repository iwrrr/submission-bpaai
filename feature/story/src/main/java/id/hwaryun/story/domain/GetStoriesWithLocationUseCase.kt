package id.hwaryun.story.domain

import id.hwaryun.core.base.BaseUseCase
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.utils.suspendSubscribe
import id.hwaryun.story.data.model.viewparam.StoryViewParam
import id.hwaryun.story.data.repository.StoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias StoryLocationResult = List<StoryViewParam>?

class GetStoriesWithLocationUseCase @Inject constructor(
    private val repository: StoryRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Nothing, StoryLocationResult>(dispatcher) {

    override suspend fun execute(param: Nothing?): Flow<ViewResource<StoryLocationResult>> = flow {
        emit(ViewResource.Loading())
        repository.getStoriesWithLocation().collect { result ->
            result.suspendSubscribe(
                doOnSuccess = { response ->
                    val stories = response.payload?.stories?.map { it.toViewParam() }
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