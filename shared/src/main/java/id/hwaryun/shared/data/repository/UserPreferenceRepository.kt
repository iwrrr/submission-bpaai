package id.hwaryun.shared.data.repository

import id.hwaryun.core.wrapper.DataResource
import id.hwaryun.shared.data.local.UserPreferenceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UserPreferenceRepository {
    suspend fun getUserToken(): Flow<DataResource<String>>
    suspend fun setUserToken(newUserToken: String): Flow<DataResource<Unit>>
    suspend fun clearData(): Flow<DataResource<Unit>>
}

class UserPreferenceRepositoryImpl @Inject constructor(
    private val dataSource: UserPreferenceDataSource
) : Repository(), UserPreferenceRepository {

    override suspend fun getUserToken(): Flow<DataResource<String>> = flow {
        emit(proceed { dataSource.getUserToken().first() })
    }

    override suspend fun setUserToken(newUserToken: String): Flow<DataResource<Unit>> = flow {
        emit(proceed { dataSource.setUserToken(newUserToken) })
    }

    override suspend fun clearData() = flow {
        emit(proceed { dataSource.clearData() })
    }
}