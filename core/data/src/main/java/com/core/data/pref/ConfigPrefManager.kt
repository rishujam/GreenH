package com.core.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.core.util.Constants
import com.core.data.di.ConfigPreferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 09/02/24.
 */

@Singleton
class ConfigPrefManager @Inject constructor(
    @ConfigPreferences private val dataStore: DataStore<Preferences>
) {

    suspend fun setMaintenance(value: Boolean) {
        dataStore.edit { data ->
            data[KEY_MAINTENANCE] = value
        }
    }

    suspend fun setForceUpdate(value: Boolean) {
        dataStore.edit { data ->
            data[KEY_FORCE_UPDATE] = value
        }
    }

    suspend fun setLatestVersion(value: Int) {
        dataStore.edit { data ->
            data[KEY_LATEST_VERSION] = value
        }
    }

    suspend fun readMaintenance(): Boolean? {
        return dataStore.data.first()[KEY_MAINTENANCE]
    }

    suspend fun readForceUpdate(): Boolean {
        return dataStore.data.first()[KEY_FORCE_UPDATE] ?: false
    }

    suspend fun readLatestVersion(): Int {
        return dataStore.data.first()[KEY_LATEST_VERSION] ?: 1
    }

    companion object {
        private val KEY_MAINTENANCE = booleanPreferencesKey("key_maintenance")
        private val KEY_FORCE_UPDATE = booleanPreferencesKey("key_force_update")
        private val KEY_LATEST_VERSION = intPreferencesKey("key_latest_version")
    }

}