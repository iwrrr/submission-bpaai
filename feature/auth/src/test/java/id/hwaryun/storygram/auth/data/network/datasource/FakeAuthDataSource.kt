package id.hwaryun.storygram.auth.data.network.datasource

import id.hwaryun.core.base.BaseResponse
import id.hwaryun.storygram.auth.data.model.request.LoginRequest
import id.hwaryun.storygram.auth.data.model.request.RegisterRequest
import id.hwaryun.storygram.auth.data.model.response.UserResponse
import id.hwaryun.storygram.auth.utils.AuthDummy

class FakeAuthDataSource : AuthDataSource {

    override suspend fun registerUser(registerRequest: RegisterRequest): BaseResponse<Nothing> {
        return AuthDummy.registered()
    }

    override suspend fun loginUser(loginRequest: LoginRequest): BaseResponse<UserResponse> {
        return AuthDummy.authenticated()
    }
}