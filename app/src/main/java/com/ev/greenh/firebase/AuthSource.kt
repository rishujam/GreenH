package com.ev.greenh.firebase

import android.util.Log
import com.ev.greenh.models.Profile
import com.ev.greenh.models.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AuthSource(
    private val auth:FirebaseAuth
) {

    private val fireRef = Firebase.firestore

    fun getAuthInstance(): FirebaseAuth {
        return auth
    }

//    suspend fun registerUser(email: String,pass:String,collection:String) : Response {
//        auth.createUserWithEmailAndPassword(email, pass).await()
//        saveProfile(collection, Profile(email))
//        return Response(true)
//    }

    suspend fun loginUser(email:String,pass: String): Response {
        auth.signInWithEmailAndPassword(email,pass).await()
        return Response(true)
    }

    suspend fun saveProfile(collection: String, profile: Profile): Response {
        return try {
            val doc = fireRef.collection(collection).document(profile.uid).get().await()
            if(doc.exists()){
                Response(true)
            }else{
                fireRef.collection(collection).document(profile.uid).set(profile).await()
                Response(true)
            }
        }catch (e:Exception){
            Response(errorMsg = e.message)
        }
    }

    suspend fun saveNotifyToken(uid:String,token:String,collection: String):Response {
        val res= Response()
        return  try {
            fireRef.collection(collection).document(uid).set(mapOf("token" to token)).await()
            res.success = true
            res
        }catch (e:Exception){
            res.errorMsg = e.message
            res
        }
    }
}