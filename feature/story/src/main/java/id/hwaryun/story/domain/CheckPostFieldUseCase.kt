package id.hwaryun.story.domain

import id.hwaryun.core.base.BaseUseCase
import id.hwaryun.core.exception.FieldErrorException
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.story.R
import id.hwaryun.story.constants.PostFieldConstants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias CheckFieldPostResult = List<Pair<Int, Int>>

class CheckPostFieldUseCase @Inject constructor(
    dispatcher: CoroutineDispatcher
) : BaseUseCase<String, CheckFieldPostResult>(dispatcher) {

    override suspend fun execute(param: String?): Flow<ViewResource<CheckFieldPostResult>> =
        flow {
            param?.let {
                val result = mutableListOf<Pair<Int, Int>>()
                checkIsDescriptionValid(param)?.let {
                    result.add(it)
                }
                if (result.isEmpty()) {
                    emit(ViewResource.Success(result))
                } else {
                    emit(ViewResource.Error(FieldErrorException(result)))
                }

            } ?: throw IllegalStateException("Param Required")
        }

    private fun checkIsDescriptionValid(description: String): Pair<Int, Int>? {
        return if (description.isEmpty()) {
            Pair(PostFieldConstants.DESCRIPTION_FIELD, R.string.error_field_description)
        } else {
            null
        }
    }
}