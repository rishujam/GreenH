package com.ev.greenh.analytics

import com.core.util.Constants
import com.ev.greenh.home.data.Analytic
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 23/11/23.
 */

class AnalyticsRepo @Inject constructor() {

    private val fireRef = Firebase.firestore

    suspend fun postAnalytics(analytic: Analytic) {
        fireRef.collection(Constants.FirebaseColl.ANALYTICS)
            .document(analytic.id)
            .set(analytic).await()
    }

}