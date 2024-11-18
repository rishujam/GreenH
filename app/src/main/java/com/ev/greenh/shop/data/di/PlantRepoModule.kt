package com.ev.greenh.shop.data.di

import com.ev.greenh.shop.data.repo.PlantRepository
import com.ev.greenh.shop.data.repo.PlantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 18/11/24.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class PlantRepoModule {

    @Singleton
    @Binds
    abstract fun plantRepository(repository: PlantRepositoryImpl): PlantRepository

}