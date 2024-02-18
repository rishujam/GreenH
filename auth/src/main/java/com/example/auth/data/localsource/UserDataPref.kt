package com.example.auth.data.localsource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.core.data.Constants
import kotlinx.coroutines.flow.first

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

class UserDataPref(
    private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.Pref.AUTH)

    suspend fun readUid(): String? {
        val preferences =context.dataStore.data.first()
        return preferences[KEY_UID]
    }

    suspend fun setUid(value:String) {
        context.dataStore.edit { authdata ->
            authdata[KEY_UID] = value
        }
    }

    suspend fun setIsLoggedIn(value: Boolean) {
        context.dataStore.edit { authData ->
            authData[KEY_LOGGED_IN] = value
        }
    }

    suspend fun isLoggedIn(): Boolean? {
        return context.dataStore.data.first()[KEY_LOGGED_IN]
    }

    suspend fun setFirebaseMsgToken(value: String) {
        context.dataStore.edit { authData ->
            authData[KEY_MSG_TOKEN] = value
        }
    }

    suspend fun getFirebaseMsgToken(): String? {
        return context.dataStore.data.first()[KEY_MSG_TOKEN]
    }

    companion object{
        private val KEY_UID = stringPreferencesKey("key_uid")
        private val KEY_LOGGED_IN = booleanPreferencesKey("key_logged_in")
        private val KEY_MSG_TOKEN = stringPreferencesKey("key_msg_token")
    }
}