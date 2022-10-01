package id.hwaryun.storygram.auth.domain

import id.hwaryun.core.base.BaseUseCase
import id.hwaryun.core.exception.FieldErrorException
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.utils.StringUtils
import id.hwaryun.storygram.auth.R
import id.hwaryun.storygram.auth.constants.RegisterFieldConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias CheckFieldRegisterResult = List<Pair<Int, Int>>

class CheckRegisterFieldUseCase @Inject constructor(
    dispatcher: CoroutineDispatcher
) : BaseUseCase<RegisterUserUseCase.Param, CheckFieldRegisterResult>(dispatcher) {

    override suspend fun execute(param: RegisterUserUseCase.Param?): Flow<ViewResource<CheckFieldRegisterResult>> =
        flow {
            param?.let { p ->
                val result = mutableListOf<Pair<Int, Int>>()
                checkIsNameValid(p.name)?.let {
                    result.add(it)
                }
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
            Pair(RegisterFieldConstants.PASSWORD_FIELD, R.string.error_field_empty)
        } else if (password.length < 6) {
            Pair(
                RegisterFieldConstants.PASSWORD_FIELD,
                R.string.error_field_password_length_below_min
            )
        } else {
            null
        }
    }

    private fun checkIsEmailValid(email: String): Pair<Int, Int>? {
        return if (email.isEmpty()) {
            Pair(RegisterFieldConstants.EMAIL_FIELD, R.string.error_field_empty)
        } else if (!StringUtils.isEmailValid(email)) {
            Pair(RegisterFieldConstants.EMAIL_FIELD, R.string.error_field_email_not_valid)
        } else {
            null
        }
    }

    private fun checkIsNameValid(name: String): Pair<Int, Int>? {
        return if (name.isEmpty()) {
            Pair(RegisterFieldConstants.NAME_FIELD, R.string.error_field_empty)
        } else if (name.length < 6) {
            Pair(
                RegisterFieldConstants.NAME_FIELD,
                R.string.error_field_name_length_below_min
            )
        } else if (name.contains(" ")) {
            Pair(
                RegisterFieldConstants.NAME_FIELD,
                R.string.error_field_name_not_allowed_character
            )
        } else {
            null
        }
    }
}