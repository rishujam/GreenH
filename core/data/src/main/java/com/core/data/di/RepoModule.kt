package com.core.data.di

import com.core.data.AppConfigRepository
import com.core.data.AppConfigRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 21/02/24.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun appConfigRepository(repository: AppConfigRepositoryImpl): AppConfigRepository

}