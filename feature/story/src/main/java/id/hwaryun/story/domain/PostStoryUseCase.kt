package id.hwaryun.story.domain

import id.hwaryun.core.base.BaseUseCase
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.utils.suspendSubscribe
import id.hwaryun.story.data.repository.StoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class PostStoryUseCase @Inject constructor(
    private val checkPostFieldUseCase: CheckPostFieldUseCase,
    private val repository: StoryRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<PostStoryUseCase.Param, Unit>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<Unit>> = flow {
        param?.let {
            emit(ViewResource.Loading())
            checkPostFieldUseCase(param.description).first().suspendSubscribe(
                doOnSuccess = {
                    val requestImageFile = param.photo.asRequestBody("image/jpeg".toMediaType())
                    val requestBody = MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("description", param.description)
                        .addFormDataPart("photo", param.photo.name, requestImageFile)
                        .addFormDataPart("lon", param.long)
                        .addFormDataPart("lat", param.lat)
                        .build()

                    repository.addNewStory(requestBody).collect { postResult ->
                        postResult.suspendSubscribe(
                            doOnSuccess = {
                                emit(ViewResource.Success(Unit))
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
        }
    }

    data class Param(
        val description: String,
        val photo: File,
        val lat: String,
        val long: String
    )
}