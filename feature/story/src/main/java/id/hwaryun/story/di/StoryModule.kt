package id.hwaryun.story.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.hwaryun.story.data.database.StoryDatabase
import id.hwaryun.story.data.network.datasource.StoryDataSource
import id.hwaryun.story.data.network.datasource.StoryDataSourceImpl
import id.hwaryun.story.data.network.service.StoryService
import id.hwaryun.story.data.repository.StoryRepository
import id.hwaryun.story.data.repository.StoryRepositoryImpl
import id.hwaryun.story.domain.CheckPostFieldUseCase
import id.hwaryun.story.domain.GetStoriesUseCase
import id.hwaryun.story.domain.GetStoriesWithLocationUseCase
import id.hwaryun.story.domain.PostStoryUseCase
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoryModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): StoryService {
        return retrofit.create(StoryService::class.java)
    }

    @Provides
    @Singleton
    fun provideStoryDatabase(app: Application): StoryDatabase {
        return Room.databaseBuilder(app, StoryDatabase::class.java, "story.db").build()
    }

    @Provides
    @Singleton
    fun provideStoryDataSource(api: StoryService): StoryDataSource {
        return StoryDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideStoryRepository(
        dataSource: StoryDataSource,
        api: StoryService,
        database: StoryDatabase
    ): StoryRepository {
        return StoryRepositoryImpl(dataSource, api, database)
    }

    @Provides
    @Singleton
    fun provideCheckPostFieldUseCase(): CheckPostFieldUseCase {
        return CheckPostFieldUseCase(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideGetStoriesWithLocationUseCase(
        repository: StoryRepository
    ): GetStoriesWithLocationUseCase {
        return GetStoriesWithLocationUseCase(
            repository,
            Dispatchers.IO
        )
    }

    @Provides
    @Singleton
    fun provideGetStoriesUseCase(
        repository: StoryRepository
    ): GetStoriesUseCase {
        return GetStoriesUseCase(
            repository,
            Dispatchers.IO
        )
    }

    @Provides
    @Singleton
    fun providePostStoryUseCase(
        checkPostFieldUseCase: CheckPostFieldUseCase,
        repository: StoryRepository
    ): PostStoryUseCase {
        return PostStoryUseCase(
            checkPostFieldUseCase,
            repository,
            Dispatchers.IO
        )
    }
}