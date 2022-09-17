package id.hwaryun.storygram.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hwaryun.shared.router.ActivityRouter
import id.hwaryun.storygram.router.ActivityRouterImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideActivityRouter(): ActivityRouter {
        return ActivityRouterImpl()
    }
}