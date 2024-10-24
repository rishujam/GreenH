package com.ev.greenh.di

import com.core.ui.nav.Navigation
import com.ev.greenh.navigation.NavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 25/04/24.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Singleton
    @Binds
    abstract fun navigation(nav: NavigationImpl): Navigation

}