package id.hwaryun.auth.data.repository

import id.hwaryun.auth.data.model.request.LoginRequest
import id.hwaryun.auth.data.model.request.RegisterRequest
import id.hwaryun.auth.data.model.response.UserResponse
import id.hwaryun.auth.data.network.datasource.AuthDataSource
import id.hwaryun.core.base.BaseResponse
import id.hwaryun.core.wrapper.DataResource
import id.hwaryun.shared.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

typealias RegisterDataResource = DataResource<BaseResponse<Nothing>>
typealias LoginDataResource = DataResource<BaseResponse<UserResponse>>

interface AuthRepository {
    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<RegisterDataResource>

    suspend fun loginUser(email: String, password: String): Flow<LoginDataResource>
}

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
) : AuthRepository, Repository() {

    override suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flow<RegisterDataResource> =
        flow {
            emit(safeNetworkCall {
                dataSource.registerUser(
                    RegisterRequest(
                        name,
                        email,
                        password
                    )
                )
            })
        }

    override suspend fun loginUser(email: String, password: String): Flow<LoginDataResource> =
        flow {
            emit(safeNetworkCall { dataSource.loginUser(LoginRequest(email, password)) })
        }
}