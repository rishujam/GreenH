package com.ev.greenh.home.data

import com.core.data.Constants
import com.ev.greenh.shop.data.repo.BaseRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 15/11/23.
 */

class HomeRepository @Inject constructor() : BaseRepository() {

    private val fireRef = Firebase.firestore

    suspend fun getTodayTip() = safeApiCall {
        val doc = fireRef.collection(Constants.FirebaseColl.TIPS)
            .document(Constants.FirebaseDoc.TODAY_TIP)
            .get().await()
        doc["tip"]?.toString()
    }

}