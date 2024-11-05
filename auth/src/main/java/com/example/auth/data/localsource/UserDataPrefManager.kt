package com.example.auth.data.localsource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.auth.di.UserPreferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

@Singleton
class UserDataPrefManager @Inject constructor(
    @UserPreferences private val dataStore: DataStore<Preferences>
) {

    suspend fun readUid(): String? {
        val preferences = dataStore.data.first()
        return preferences[KEY_UID]
    }

    suspend fun setUid(value:String?) {
        value?.let {
            dataStore.edit { authdata ->
                authdata[KEY_UID] = value
            }
        }
    }

    suspend fun setIsLoggedIn(value: Boolean) {
        dataStore.edit { authData ->
            authData[KEY_LOGGED_IN] = value
        }
    }

    suspend fun isLoggedIn(): Boolean? {
        return dataStore.data.first()[KEY_LOGGED_IN]
    }

    suspend fun setFirebaseMsgToken(value: String) {
        dataStore.edit { authData ->
            authData[KEY_MSG_TOKEN] = value
        }
    }

    suspend fun getFirebaseMsgToken(): String? {
        return dataStore.data.first()[KEY_MSG_TOKEN]
    }

    companion object{
        private val KEY_UID = stringPreferencesKey("key_uid")
        private val KEY_LOGGED_IN = booleanPreferencesKey("key_logged_in")
        private val KEY_MSG_TOKEN = stringPreferencesKey("key_msg_token")
    }
}