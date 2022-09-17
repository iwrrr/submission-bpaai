package id.hwaryun.shared.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hwaryun.shared.data.local.UserPreferenceDataSource
import id.hwaryun.shared.data.repository.UserPreferenceRepository
import id.hwaryun.shared.data.repository.UserPreferenceRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserPreferenceRepository(dataSource: UserPreferenceDataSource): UserPreferenceRepository {
        return UserPreferenceRepositoryImpl(dataSource)
    }
}