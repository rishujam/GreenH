package com.example.auth.di

import com.example.auth.domain.ProductFlavour
import com.example.auth.data.remotesource.PhoneAuthApi
import com.example.auth.data.repository.PhoneAuthRepository
import com.example.auth.data.repositoryimpl.PhoneAuthRepoImpl
import com.example.auth.data.repositoryimpl.PhoneAuthStagingRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 26/10/24.
 */

@Module
@InstallIn(SingletonComponent::class)
object RepoProvider {

    @Provides
    @Singleton
    fun provideRepo(api: PhoneAuthApi, flavour: ProductFlavour): PhoneAuthRepository {
        return if(flavour.get() == "staging") {
            PhoneAuthStagingRepoImpl()
        } else {
            PhoneAuthRepoImpl(api)
        }
    }

}