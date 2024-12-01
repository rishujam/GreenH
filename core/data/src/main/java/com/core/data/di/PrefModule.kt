package com.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/*
 * Created by Sudhanshu Kumar on 20/02/24.
 */
@Module
@InstallIn(SingletonComponent::class)
object PrefModule {

    @CommonPreferences
    @Provides
    fun provideCommonPref(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(Constants.Pref.COMMON) }
        )
    }

    @ConfigPreferences
    @Provides
    fun provideConfigPref(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(Constants.Pref.CONFIG) }
        )
    }

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CommonPreferences

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigPreferences