package com.ev.greenh

import com.example.auth.domain.ProductFlavour
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 26/10/24.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductFlavourModule {

    @Singleton
    @Binds
    abstract fun productFlavour(flavour: ProductFlavourImpl): ProductFlavour

}