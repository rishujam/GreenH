package com.ev.greenh.plantidentify.data.repo

import android.net.Uri
import com.core.data.remote.ApiIdentifier
import com.core.data.remote.RetrofitPool
import com.ev.greenh.plantidentify.data.PlantNetApi
import com.ev.greenh.plantidentify.data.model.req.PlantIdentifyReq
import com.ev.greenh.repository.BaseRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

class PlantIdentifyRepo @Inject constructor() : BaseRepository() {

    private val TAG = "PlantIdentificationRepo"

    private val firebaseStorage = Firebase.storage.reference

    private val api = RetrofitPool.getApi(
        ApiIdentifier.PlantNetApi,
        PlantNetApi::class.java
    ).service as? PlantNetApi

    suspend fun uploadPlantToIdentify(
        fileName: String,
        image: Uri
    ) = safeApiCall {
        try {
            firebaseStorage.child(fileName).putFile(image).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun uploadPlantToIdentifyBytes(
        fileName: String,
        image: ByteArray
    ) = safeApiCall {
        try {
            firebaseStorage.child(fileName).putBytes(image).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getPublicUrlOfFileFirebase(
        location: String
    ) = safeApiCall {
        firebaseStorage.child(location).downloadUrl.await().toString()
    }

    suspend fun identifyPlant(req: PlantIdentifyReq) = safeApiCall {
        val res = api?.identifyPlant(
            images = req.imageUrls,
            organs = req.organs
        )
        if (res?.isSuccessful == true) {
            return@safeApiCall res.body()
        }
        return@safeApiCall null
    }
}