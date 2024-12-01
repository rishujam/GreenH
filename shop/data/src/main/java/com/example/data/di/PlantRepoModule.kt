package com.example.data.di

import com.example.data.repo.PlantRepositoryImpl
import com.example.domain.repo.PlantRepository
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

    @Binds
    @Singleton
    abstract fun bindPlantRepository(impl: PlantRepositoryImpl): PlantRepository

}