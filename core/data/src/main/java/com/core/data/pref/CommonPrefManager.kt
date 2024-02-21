package com.core.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.core.data.Constants
import com.core.data.di.CommonPreferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

@Singleton
class CommonPrefManager @Inject constructor(
    @CommonPreferences private val dataStore: DataStore<Preferences>
) {



}