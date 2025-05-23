package com.core.data.remote

import android.util.Log
import com.core.util.Constants
import com.core.data.model.Feature
import com.core.data.model.ResFeatureConfig
import com.core.data.model.ResMaintenance
import com.core.data.model.ResUpdate
import com.core.data.model.Update
import com.core.util.toObject
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

class ConfigDataSource @Inject constructor() {

    private val fireRef = Firebase.firestore

    suspend fun checkMaintenance(): ResMaintenance {
        return try {
            val doc = fireRef.collection(Constants.FirebaseColl.APP_CONFIG)
                .document(Constants.FirebaseDoc.MAINTENANCE)
                .get().await()
            val maintenance = doc.getBoolean("maintenance") ?: false
            ResMaintenance(true, maintenance = maintenance)
        } catch (e: Exception) {
            ResMaintenance(false, e.message.toString())
        }
    }

    suspend fun checkUpdate(): ResUpdate {
        return try {
            val doc = fireRef.collection(Constants.FirebaseColl.APP_CONFIG)
                .document(Constants.FirebaseDoc.UPDATE)
                .get().await()
            val latestVersion = doc.getString("liveVersion")?.toInt() ?: 1
            val forceUpdate = doc.getBoolean("forceUpdate") ?: false
            ResUpdate(true, data = Update(forceUpdate, latestVersion))
        } catch (e: Exception) {
            ResUpdate(false, e.message)
        }
    }

    suspend fun getFeatureConfig(): ResFeatureConfig {
        return try {
            val docs = fireRef.collection(Constants.FirebaseColl.APP_CONFIG)
                .document(Constants.FirebaseDoc.FEATURE)
                .collection(Constants.FirebaseColl.FEATURE_CONFIG)
                .get().await()
            val featureConfigList = mutableListOf<Feature>()
            for(i in docs) {
                try {
                    val feature = i.data.toObject(Feature::class)
//                    val feature = i.toObject<Feature>()
                    featureConfigList.add(feature)
                } catch (e: Exception) {
                    //TODO Send analytics
                    Log.d("Analytics", "Error: ${e.message}")
                }
            }
            ResFeatureConfig(true, featureList = featureConfigList)
        } catch (e: Exception) {
            ResFeatureConfig(false, e.message)
        }
    }

}