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

class RegisterUserUseCase @Inject constructor(
    private val checkRegisterFieldUseCase: CheckRegisterFieldUseCase,
    private val setUserTokenUseCase: SetUserTokenUseCase,
    private val repository: AuthRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<RegisterUserUseCase.Param, UserViewParam?>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<UserViewParam?>> = flow {
        param?.let {
            emit(ViewResource.Loading())
            checkRegisterFieldUseCase(param).first().suspendSubscribe(
                doOnSuccess = { _ ->
                    repository.registerUser(
                        name = param.name,
                        email = param.email,
                        password = param.password
                    ).collect { registerResult ->
                        registerResult.suspendSubscribe(
                            doOnSuccess = {
                                repository.loginUser(param.email, param.password)
                                    .collect { loginResult ->
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
                                            }, doOnError = { error ->
                                                emit(ViewResource.Error(error.exception))
                                            }
                                        )
                                    }
                            }, doOnError = { error ->
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

    data class Param(
        val name: String,
        val email: String,
        val password: String,
    )
}