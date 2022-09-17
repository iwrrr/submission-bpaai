package id.hwaryun.auth.domain

import com.catnip.core.base.BaseUseCase
import id.hwaryun.auth.data.model.viewparam.UserViewParam
import id.hwaryun.auth.data.repository.AuthRepository
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.domain.SetUserTokenUseCase
import id.hwaryun.shared.utils.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val checkLoginFieldUseCase: CheckLoginFieldUseCase,
    private val setUserTokenUseCase: SetUserTokenUseCase,
    private val repository: AuthRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<LoginUserUseCase.Param, UserViewParam?>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<UserViewParam?>> = flow {
        param?.let {
            emit(ViewResource.Loading())
            checkLoginFieldUseCase(param).first().suspendSubscribe(
                doOnSuccess = { _ ->
                    repository.loginUser(param.email, param.password).collect { loginResult ->
                        loginResult.suspendSubscribe(
                            doOnSuccess = {
                                val result = loginResult.payload
                                val token = result?.user?.token
                                val user = result?.user
                                if (!token.isNullOrEmpty() && user != null) {
                                    setUserTokenUseCase(token).collect {
                                        it.suspendSubscribe(
                                            doOnSuccess = {
                                                emit(ViewResource.Success(user.toViewParam()))
                                            },
                                            doOnError = { error ->
                                                emit(ViewResource.Error(error.exception))
                                            }
                                        )
                                    }
                                }
                            },
                            doOnError = { error ->
                                emit(ViewResource.Error(error.exception))
                            }
                        )
                    }
                },
                doOnError = { error ->
                    emit(ViewResource.Error(error.exception))
                }
            )
        } ?: throw IllegalStateException("Param Required")
    }

    data class Param(val email: String, val password: String)
}