package com.nims.fruitful.data.service.module

import com.nims.fruitful.data.service.AccountService
import com.nims.fruitful.data.service.LogService
import com.nims.fruitful.data.service.StorageService
import com.nims.fruitful.data.service.impl.AccountServiceImpl
import com.nims.fruitful.data.service.impl.LogServiceImpl
import com.nims.fruitful.data.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun providesAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun providesLogService(impl: LogServiceImpl): LogService
}