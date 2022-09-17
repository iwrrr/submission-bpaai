package id.hwaryun.shared.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.hwaryun.shared.data.local.UserPreferenceDataSource
import id.hwaryun.shared.data.local.UserPreferenceDataSourceImpl
import id.hwaryun.shared.data.local.UserPreferenceFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideUserPreferenceFactory(@ApplicationContext context: Context): DataStore<Preferences> {
        return UserPreferenceFactory(context).create()
    }

    @Provides
    @Singleton
    fun provideUserPreferenceDataSource(
        dataStore: DataStore<Preferences>
    ): UserPreferenceDataSource {
        return UserPreferenceDataSourceImpl(dataStore)
    }
}