package id.hwaryun.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hwaryun.core.wrapper.ViewResource
import id.hwaryun.shared.domain.GetUserTokenUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase
) : ViewModel() {

    val tokenResult = MutableLiveData<ViewResource<String>>()

    fun getToken() {
        viewModelScope.launch {
            getUserTokenUseCase().collect {
                tokenResult.postValue(it)
            }
        }
    }
}