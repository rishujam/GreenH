package com.example.auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.core.data.Constants
import com.example.auth.data.localsource.UserDataPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 18/02/24.
 */

@Module
@InstallIn(SingletonComponent::class)
object UserPrefModule {

    @UserPreferences
    @Provides
    fun provideUserDataPref(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(Constants.Pref.AUTH) }
        )
    }

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserPreferences