package id.hwaryun.auth.data.network.datasource

import id.hwaryun.auth.data.model.request.LoginRequest
import id.hwaryun.auth.data.model.request.RegisterRequest
import id.hwaryun.auth.data.model.response.UserResponse
import id.hwaryun.auth.data.network.service.AuthService
import id.hwaryun.core.base.BaseResponse
import javax.inject.Inject

interface AuthDataSource {
    suspend fun registerUser(registerRequest: RegisterRequest): BaseResponse<Nothing>
    suspend fun loginUser(loginRequest: LoginRequest): BaseResponse<UserResponse>
}

class AuthDataSourceImpl @Inject constructor(
    private val api: AuthService
) : AuthDataSource {

    override suspend fun registerUser(registerRequest: RegisterRequest): BaseResponse<Nothing> {
        return api.registerUser(registerRequest)
    }

    override suspend fun loginUser(loginRequest: LoginRequest): BaseResponse<UserResponse> {
        return api.loginUser(loginRequest)
    }
}