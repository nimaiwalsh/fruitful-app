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
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun providesAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    @Binds
    abstract fun providesLogService(impl: LogServiceImpl): LogService
}