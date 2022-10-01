package id.hwaryun.shared.domain

import id.hwaryun.core.base.BaseUseCase
import id.hwaryun.core.wrapper.DataResource
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.data.repository.UserPreferenceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetUserTokenUseCase @Inject constructor(
    private val repository: UserPreferenceRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<String, Boolean>(dispatcher) {

    override suspend fun execute(param: String?): Flow<ViewResource<Boolean>> = flow {
        param?.let { token ->
            val setToken = repository.setUserToken(token).first()

            if (setToken is DataResource.Success) {
                emit(ViewResource.Success(true))
            } else {
                emit(ViewResource.Error(IllegalStateException("Failed to save token")))
            }
        }
    }
}