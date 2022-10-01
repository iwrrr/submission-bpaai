package id.hwaryun.story.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.domain.ClearUserTokenUseCase
import id.hwaryun.shared.domain.GetUserTokenUseCase
import id.hwaryun.story.domain.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val clearUserTokenUseCase: ClearUserTokenUseCase,
    private val getStoriesWithLocationUseCase: GetStoriesWithLocationUseCase,
    private val getStoriesUseCase: GetStoriesUseCase,
    private val postStoryUseCase: PostStoryUseCase
) : ViewModel() {

    val tokenResult = MutableLiveData<ViewResource<String>>()
    val logoutResult = MutableLiveData<ViewResource<Unit?>>()
    val storyResult = MutableLiveData<ViewResource<StoryResult>>()
    val storyLocationResult = MutableLiveData<ViewResource<StoryLocationResult>>()
    val postResult = MutableLiveData<ViewResource<Unit>>()

    fun getToken() {
        viewModelScope.launch {
            getUserTokenUseCase().collect {
                tokenResult.postValue(it)
            }
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            clearUserTokenUseCase().collect {
                logoutResult.postValue(it)
            }
        }
    }

    fun getStoriesWithLocation() {
        viewModelScope.launch {
            getStoriesWithLocationUseCase().collect {
                storyLocationResult.postValue(it)
            }
        }
    }

    fun getStories() {
        viewModelScope.launch {
            getStoriesUseCase(viewModelScope).collect {
                storyResult.postValue(it)
            }
        }
    }

    fun postStory(description: String, photo: File, lat: Double, long: Double) {
        viewModelScope.launch {
            postStoryUseCase(
                PostStoryUseCase.Param(
                    description,
                    photo,
                    lat.toString(),
                    long.toString()
                )
            ).collect {
                postResult.postValue(it)
            }
        }
    }
}