package com.nims.fruitful.data.repository.module

import com.nims.fruitful.data.repository.IdeaRepository
import com.nims.fruitful.data.repository.IdeaRepositoryImpl
import com.nims.fruitful.data.service.StorageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideIdeaRepository(storageService: StorageService): IdeaRepository {
        return IdeaRepositoryImpl(storageService)
    }
}