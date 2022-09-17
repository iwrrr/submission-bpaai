package id.hwaryun.auth.data.network.service

import id.hwaryun.auth.data.model.request.LoginRequest
import id.hwaryun.auth.data.model.request.RegisterRequest
import id.hwaryun.auth.data.model.response.UserResponse
import id.hwaryun.core.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): BaseResponse<Nothing>

    @POST("login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): BaseResponse<UserResponse>
}