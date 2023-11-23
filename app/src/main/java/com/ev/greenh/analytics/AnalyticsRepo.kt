package com.ev.greenh.analytics

import com.ev.greenh.home.data.Analytic
import com.ev.greenh.repository.BaseRepository
import com.ev.greenh.util.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/*
 * Created by Sudhanshu Kumar on 23/11/23.
 */

class AnalyticsRepo : BaseRepository() {

    private val fireRef = Firebase.firestore

    suspend fun postAnalytics(analytic: Analytic) = safeApiCall {
        fireRef.collection(Constants.FirebaseCollection.ANALYTICS)
            .document(analytic.id)
            .set(analytic).await()
    }

}