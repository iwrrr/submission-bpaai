package id.hwaryun.auth.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hwaryun.auth.data.model.viewparam.UserViewParam
import id.hwaryun.auth.domain.LoginUserUseCase
import id.hwaryun.auth.domain.RegisterUserUseCase
import id.hwaryun.core.wrapper.ViewResource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    val registerResult = MutableLiveData<ViewResource<UserViewParam?>>()
    val loginResult = MutableLiveData<ViewResource<UserViewParam?>>()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            loginUserUseCase(LoginUserUseCase.Param(email, password)).collect {
                loginResult.postValue(it)
            }
        }
    }

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            registerUserUseCase(RegisterUserUseCase.Param(name, email, password)).collect {
                registerResult.postValue(it)
            }
        }
    }
}