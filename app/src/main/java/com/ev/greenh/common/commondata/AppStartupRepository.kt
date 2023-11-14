package com.ev.greenh.common.commondata

import com.ev.greenh.common.commondata.model.Feature
import com.ev.greenh.common.commondata.model.FeatureList
import com.ev.greenh.repository.BaseRepository
import com.ev.greenh.util.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

/*
 * Created by Sudhanshu Kumar on 12/11/23.
 */

class AppStartupRepository : BaseRepository() {

    private val fireRef = Firebase.firestore

    suspend fun getFeaturesList() = safeApiCall {
        val list = mutableListOf<Feature>()
        val data = fireRef.collection(Constants.FirebaseCollection.FEATURE).get().await()
        for (i in data.documents) {
            val plant = i.toObject<Feature>()
            plant?.let { list.add(it) }
        }
        FeatureList(list)
    }

}