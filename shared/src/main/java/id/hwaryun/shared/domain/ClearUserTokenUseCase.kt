package id.hwaryun.shared.domain

import com.catnip.core.base.BaseUseCase
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.data.repository.UserPreferenceRepository
import id.hwaryun.shared.utils.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearUserTokenUseCase @Inject constructor(
    private val repository: UserPreferenceRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Nothing, Unit?>(dispatcher) {

    override suspend fun execute(param: Nothing?): Flow<ViewResource<Unit?>> = flow {
        repository.clearData().collect {
            it.suspendSubscribe(
                doOnSuccess = { result ->
                    emit(ViewResource.Success(result.payload))
                },
                doOnError = { error ->
                    emit(ViewResource.Error(error.exception))
                }
            )
        }
    }
}