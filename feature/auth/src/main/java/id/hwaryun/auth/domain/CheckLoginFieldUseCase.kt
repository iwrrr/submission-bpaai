package id.hwaryun.auth.domain

import com.catnip.core.base.BaseUseCase
import id.hwaryun.auth.R
import id.hwaryun.auth.constants.LoginFieldConstants
import id.hwaryun.core.exception.FieldErrorException
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.utils.StringUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias CheckFieldLoginResult = List<Pair<Int, Int>>

class CheckLoginFieldUseCase @Inject constructor(
    dispatcher: CoroutineDispatcher
) : BaseUseCase<LoginUserUseCase.Param, CheckFieldLoginResult>(dispatcher) {

    override suspend fun execute(param: LoginUserUseCase.Param?): Flow<ViewResource<CheckFieldLoginResult>> =
        flow {
            param?.let { p ->
                val result = mutableListOf<Pair<Int, Int>>()
                checkIsEmailValid(p.email)?.let {
                    result.add(it)
                }
                checkIsPasswordValid(p.password)?.let {
                    result.add(it)
                }
                if (result.isEmpty()) {
                    emit(ViewResource.Success(result))
                } else {
                    emit(ViewResource.Error(FieldErrorException(result)))
                }

            } ?: throw IllegalStateException("Param Required")
        }

    private fun checkIsPasswordValid(password: String): Pair<Int, Int>? {
        return if (password.isEmpty()) {
            Pair(LoginFieldConstants.PASSWORD_FIELD, R.string.error_field_password)
        } else if (password.length < 6) {
            Pair(LoginFieldConstants.PASSWORD_FIELD, R.string.error_field_password_length_below_min)
        } else {
            null
        }
    }

    private fun checkIsEmailValid(email: String): Pair<Int, Int>? {
        return if (email.isEmpty()) {
            Pair(LoginFieldConstants.EMAIL_FIELD, R.string.error_field_email)
        } else if (!StringUtils.isEmailValid(email)) {
            Pair(LoginFieldConstants.EMAIL_FIELD, R.string.error_field_email_not_valid)
        } else {
            null
        }
    }
}