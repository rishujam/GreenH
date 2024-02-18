package com.core.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.core.data.Constants
import kotlinx.coroutines.flow.first

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

class CommonPref(
    private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.Pref.COMMON)

    suspend fun setUid(value:String) {
        context.dataStore.edit { data ->
            data[KEY_UID] = value
        }
    }

    suspend fun readUid(): String? {
        val preferences = context.dataStore.data.first()
        return preferences[KEY_UID]
    }

    companion object {
        private val KEY_UID = stringPreferencesKey("key_uid")
    }

}