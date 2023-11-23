package com.ev.greenh.auth.data


import android.util.Log
import com.ev.greenh.firebase.AuthSource
import com.ev.greenh.localdatastore.UserPreferences
import com.ev.greenh.models.Profile
import com.ev.greenh.repository.BaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val source: AuthSource,
    private val preferences: UserPreferences
) : BaseRepository(){

    suspend fun saveUserProfile(collection: String,profile: Profile) = safeApiCall {
        source.saveProfile(collection, profile)
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

    fun sendOtp(options: PhoneAuthOptions?) {
        options?.let {
            Log.d("RishuTest", "Send Otp")
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    fun getAuth(): FirebaseAuth {
        return source.getAuthInstance()
    }
}