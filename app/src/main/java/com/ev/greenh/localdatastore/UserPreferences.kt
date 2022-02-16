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
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_data")
    val key = stringPreferencesKey("email")

    suspend fun setData(value:String) {
        context.dataStore.edit { authdata ->
            authdata[key] = value
        }
    }

    suspend fun readData():String?{
        val preferences = context.dataStore.data.first()
        return preferences[key]
    }
}