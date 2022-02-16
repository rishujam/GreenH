package com.ev.greenh.firebase

import com.ev.greenh.models.Profile
import com.ev.greenh.models.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthSource(
    val auth:FirebaseAuth
) {

    private val fireRef = Firebase.firestore

    suspend fun registerUser(email: String,pass:String,collection:String) : Response {
        auth.createUserWithEmailAndPassword(email, pass).await()
        saveProfile(collection, Profile(email))
        return Response(true)
    }

    suspend fun loginUser(email:String,pass: String): Response {
        auth.signInWithEmailAndPassword(email,pass).await()
        return Response(true)
    }

    suspend fun saveProfile(collection: String,profile: Profile): Response {
        fireRef.collection(collection).document(profile.emailId).set(profile).await()
        return Response(true)
    }
}