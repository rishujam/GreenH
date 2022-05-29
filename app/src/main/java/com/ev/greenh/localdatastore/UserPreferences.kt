package com.ev.greenh.localdatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferences(
    private val context: Context
){
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_data")

    suspend fun read(): String? {
        val preferences =context.dataStore.data.first()
        return preferences[KEY_EMAIL]
    }

    suspend fun setData(value:String) {
        context.dataStore.edit { authdata ->
            authdata[KEY_EMAIL] = value
        }
    }

    suspend fun clearData(){
        context.dataStore.edit {
            it.remove(KEY_EMAIL)
            it.clear()
        }
    }

    companion object{
        private val KEY_EMAIL = stringPreferencesKey("key_email")
    }
}