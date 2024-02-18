package com.example.auth.data.remotesource

import com.core.data.Constants
import com.core.data.model.ResUidGen
import com.example.auth.data.model.ResFirebaseMsgToken
import com.example.auth.data.model.ResGetProfile
import com.example.auth.data.model.ResSaveProfile
import com.example.auth.data.model.ResUserExist
import com.example.auth.data.model.UserProfile
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

/*
 * Created by Sudhanshu Kumar on 18/01/24.
 */

class UserDataSource {

    private val fireRef = Firebase.firestore

    suspend fun checkUserExist(phone: String): ResUserExist {
        return try {
            val docs = fireRef.collection(Constants.FirebaseColl.COLL_PROFILE)
                .whereEqualTo("phone", phone)
                .get().await()
            if(docs.documents.size < 1) {
                ResUserExist(
                    true,
                    exist = false,
                    msg = "No user found"
                )
            } else if(docs.documents.size > 1) {
                ResUserExist(
                    true,
                    exist = false,
                    msg = "More than one user"
                )
            } else {
                val profile = docs.documents[0].toObject<UserProfile>()
                ResUserExist(
                    true,
                    exist = true,
                    userProfile = profile
                )
            }
        } catch (e: Exception) {
            ResUserExist(success = false, msg = e.message)
        }
    }

    suspend fun saveNewUser(profile: UserProfile): ResSaveProfile {
        return try {
            fireRef.collection(Constants.FirebaseColl.COLL_PROFILE)
                .document(profile.uid).set(profile).await()
            ResSaveProfile(true)
        } catch (e: Exception) {
            ResSaveProfile(false, e.message)
        }
    }

    suspend fun getProfile(uid: String): ResGetProfile {
        return try {
            val doc = fireRef.collection(Constants.FirebaseColl.COLL_PROFILE)
                .document(uid).get().await()
            if(doc.exists()) {
                val profile = doc.toObject<UserProfile>()
                ResGetProfile(true, null, profile)
            } else {
                ResGetProfile(false, "profile not found")
            }
        } catch (e: Exception) {
            ResGetProfile(false, e.message)
        }
    }

    suspend fun getRecentlyGeneratedUid(): ResUidGen {
        return try {
            val doc = fireRef.collection(Constants.FirebaseColl.UTIL)
                .document(Constants.FirebaseDoc.UID_GEN).get().await()
            val uid = doc.getString(Constants.FirebaseField.UID)
            uid?.let {
                ResUidGen(true, uid = uid)
            } ?: run {
                ResUidGen(true, "No data found")
            }
        } catch (e: Exception) {
            ResUidGen(false, e.message)
        }
    }

    suspend fun getFirebaseMsgToken(): String? {
        return FirebaseMessaging.getInstance().token.await()
    }

    suspend fun saveFirebaseMsgToken(uid: String, token: String) {
        fireRef.collection(Constants.FirebaseColl.MSG_TOKEN)
            .document(uid).set(mapOf(Constants.FirebaseField.TOKEN to token)).await()
    }

}