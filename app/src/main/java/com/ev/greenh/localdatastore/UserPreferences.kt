package com.ev.greenh.localdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class UserPreferences(
    private val context: Context
){
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_data")

    suspend fun readUid(): String? {
        val preferences =context.dataStore.data.first()
        return preferences[KEY_EMAIL]
    }

    suspend fun setUid(value:String) {
        context.dataStore.edit { authdata ->
            authdata[KEY_EMAIL] = value
        }
    }

    companion object{
        private val KEY_EMAIL = stringPreferencesKey("key_email")
    }
}