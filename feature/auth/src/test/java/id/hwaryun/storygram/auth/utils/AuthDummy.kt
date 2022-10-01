package id.hwaryun.storygram.auth.utils

import id.hwaryun.core.base.BaseResponse
import id.hwaryun.storygram.auth.data.model.request.LoginRequest
import id.hwaryun.storygram.auth.data.model.request.RegisterRequest
import id.hwaryun.storygram.auth.data.model.response.UserResponse
import id.hwaryun.storygram.auth.data.model.viewparam.UserViewParam
import id.hwaryun.storygram.auth.domain.LoginUserUseCase
import id.hwaryun.storygram.auth.domain.RegisterUserUseCase

object AuthDummy {

    fun registered() = BaseResponse(
        error = false,
        message = "User Created",
        user = null,
        stories = null
    )

    fun authenticated() = BaseResponse(
        error = false,
        message = "success",
        user = UserResponse(
            userId = "id",
            name = "user",
            token = "token"
        ),
        stories = null
    )

    fun getUser() = UserViewParam(
        userId = "id",
        name = "user",
        token = "token"
    )

    fun validLoginParam() = LoginUserUseCase.Param(
        email = "email@email.com",
        password = "password",
    )

    fun validRegisterParam() = RegisterUserUseCase.Param(
        name = "name",
        email = "email@email.com",
        password = "password",
    )

    fun invalidLoginParam() = LoginUserUseCase.Param(
        email = "",
        password = "",
    )

    fun validLoginRequest() = LoginRequest(
        email = "email@email.com",
        password = "password",
    )

    fun validRegisterRequest() = RegisterRequest(
        name = "name",
        email = "email@email.com",
        password = "password",
    )
}