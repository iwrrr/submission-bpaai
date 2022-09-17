package id.hwaryun.shared.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class UserPreferenceFactory(private val appContext: Context) {

    fun create(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCE) }
        )
    }

    companion object {
        const val USER_PREFERENCE = "user_preferences"
    }
}

object UserPreferenceKey {
    val userToken = stringPreferencesKey(PreferenceKey.PREF_USER_TOKEN)
}

object PreferenceKey {
    const val PREF_USER_TOKEN = "PREF_USER_TOKEN"
}