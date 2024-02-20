package com.core.data.di

import android.content.Context
import androidx.room.Room
import com.core.data.localstorage.ConfigDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 20/02/24.
 */

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    @Singleton
    fun provideConfigDB(@ApplicationContext context: Context): ConfigDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ConfigDatabase::class.java,
            "config_db.db"
        ).build()
    }

}