package com.example.auth.di

import com.example.auth.data.repository.PhoneAuthRepository
import com.example.auth.data.repository.UserDataRepository
import com.example.auth.data.repositoryimpl.PhoneAuthRepoImpl
import com.example.auth.data.repositoryimpl.UserDataRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 18/02/24.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Singleton
    @Binds
    abstract fun userDataRepository(repository: UserDataRepoImpl): UserDataRepository

    @Singleton
    @Binds
    abstract fun phoneAuthRepository(repository: PhoneAuthRepoImpl): PhoneAuthRepository

}