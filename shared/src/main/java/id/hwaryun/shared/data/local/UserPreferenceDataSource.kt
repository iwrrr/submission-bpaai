package id.hwaryun.shared.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UserPreferenceDataSource {
    suspend fun clearData()
    suspend fun getUserToken(): Flow<String>
    suspend fun setUserToken(newUserToken: String)
}

class UserPreferenceDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferenceDataSource {

    override suspend fun clearData() {
        dataStore.edit {
            it.clear()
        }
    }

    override suspend fun getUserToken(): Flow<String> {
        return dataStore.data.map {
            it.toPreferences()[UserPreferenceKey.userToken].orEmpty()
        }
    }

    override suspend fun setUserToken(newUserToken: String) {
        dataStore.edit {
            it[UserPreferenceKey.userToken] = newUserToken
        }
    }
}