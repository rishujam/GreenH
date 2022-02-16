package com.ev.greenh.repository

import com.ev.greenh.firebase.AuthSource
import com.ev.greenh.localdatastore.UserPreferences

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

    suspend fun registerUser(
        email: String,
        pass: String
    ) = safeApiCall {
        source.registerUser(email,pass, "users")
    }

    suspend fun saveAuthEmail(email:String){
        preferences.setData(email)
    }

    suspend fun readEmail(){
        preferences.readData()
    }
}