package com.ev.greenh.home.data

import com.ev.greenh.repository.BaseRepository
import com.ev.greenh.util.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/*
 * Created by Sudhanshu Kumar on 15/11/23.
 */

class HomeRepository : BaseRepository() {

    private val fireRef = Firebase.firestore

    suspend fun getTodayTip() = safeApiCall {
        val doc = fireRef.collection(Constants.FirebaseCollection.TIPS)
            .document(Constants.FirebaseDoc.TODAY_TIP)
            .get().await()
        doc["tip"]?.toString()
    }

}