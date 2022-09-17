package id.hwaryun.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hwaryun.auth.data.network.datasource.AuthDataSource
import id.hwaryun.auth.data.network.datasource.AuthDataSourceImpl
import id.hwaryun.auth.data.network.service.AuthService
import id.hwaryun.auth.data.repository.AuthRepository
import id.hwaryun.auth.data.repository.AuthRepositoryImpl
import id.hwaryun.auth.domain.CheckLoginFieldUseCase
import id.hwaryun.auth.domain.CheckRegisterFieldUseCase
import id.hwaryun.auth.domain.LoginUserUseCase
import id.hwaryun.auth.domain.RegisterUserUseCase
import id.hwaryun.shared.domain.SetUserTokenUseCase
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(api: AuthService): AuthDataSource {
        return AuthDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(dataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideCheckRegisterFieldUseCase(): CheckRegisterFieldUseCase {
        return CheckRegisterFieldUseCase(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideCheckLoginFieldUseCase(): CheckLoginFieldUseCase {
        return CheckLoginFieldUseCase(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(
        checkRegisterFieldUseCase: CheckRegisterFieldUseCase,
        setUserTokenUseCase: SetUserTokenUseCase,
        repository: AuthRepository,
    ): RegisterUserUseCase {
        return RegisterUserUseCase(
            checkRegisterFieldUseCase,
            setUserTokenUseCase,
            repository,
            Dispatchers.IO
        )
    }

    @Provides
    @Singleton
    fun provideLoginUserUseCase(
        checkLoginFieldUseCase: CheckLoginFieldUseCase,
        setUserTokenUseCase: SetUserTokenUseCase,
        repository: AuthRepository,
    ): LoginUserUseCase {
        return LoginUserUseCase(
            checkLoginFieldUseCase,
            setUserTokenUseCase,
            repository,
            Dispatchers.IO
        )
    }
}