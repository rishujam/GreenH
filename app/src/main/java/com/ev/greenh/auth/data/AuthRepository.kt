package com.ev.greenh.auth.data


import com.ev.greenh.firebase.AuthSource
import com.ev.greenh.localdatastore.UserPreferences
import com.ev.greenh.models.Profile
import com.ev.greenh.repository.BaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

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

    suspend fun verifyUser(
        otp: String,
        verifyId: String?,
        phone: String,
        userColl: String,
        tokenColl: String
    ): Boolean {
        val auth = FirebaseAuth.getInstance()
        verifyId?.let {
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                it, otp
            )
            val task = auth.signInWithCredential(credential).await()
            task?.let {
                val uid = auth.currentUser?.uid.toString()
                val profile = Profile(phone = phone, uid = uid)
                saveUserProfile(userColl, profile)
                val token = FirebaseMessaging.getInstance().token.await()
                source.saveNotifyToken(uid, token, tokenColl)
                preferences.setUid(uid)
                return true
            }
            return false
        }
        return false
    }

    fun getAuth(): FirebaseAuth {
        return source.getAuthInstance()
    }
}