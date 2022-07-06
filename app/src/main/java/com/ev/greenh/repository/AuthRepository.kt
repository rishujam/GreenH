package com.ev.greenh.repository

import com.ev.greenh.firebase.AuthSource
import com.ev.greenh.localdatastore.UserPreferences
import com.ev.greenh.models.Profile

class AuthRepository(
    private val source: AuthSource,
    private val preferences: UserPreferences
) : BaseRepository(){

    suspend fun login(
        email:String,
        pass:String
    ) = safeApiCall {
        source.loginUser(email, pass)
    }

    suspend fun saveUserProfile(collection: String,profile: Profile) = safeApiCall {
        source.saveProfile(collection, profile)
    }

    suspend fun saveUidLocally(email:String){
        preferences.setUid(email)
    }

    suspend fun saveNotifyToken(uid:String,token:String,collection: String) = safeApiCall {
        source.saveNotifyToken(uid,token,collection)
    }
}