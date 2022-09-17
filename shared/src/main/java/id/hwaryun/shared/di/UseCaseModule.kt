package id.hwaryun.shared.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hwaryun.shared.data.repository.UserPreferenceRepository
import id.hwaryun.shared.domain.ClearUserTokenUseCase
import id.hwaryun.shared.domain.GetUserTokenUseCase
import id.hwaryun.shared.domain.SetUserTokenUseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetUserTokenUseCase(
        repository: UserPreferenceRepository,
    ): GetUserTokenUseCase {
        return GetUserTokenUseCase(repository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideSetUserTokenUseCase(
        repository: UserPreferenceRepository
    ): SetUserTokenUseCase {
        return SetUserTokenUseCase(repository, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideClearUserTokenUseCase(
        repository: UserPreferenceRepository
    ): ClearUserTokenUseCase {
        return ClearUserTokenUseCase(repository, Dispatchers.IO)
    }
}