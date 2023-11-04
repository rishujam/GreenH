package com.ev.greenh.plantidentify.data.repo

import android.net.Uri
import android.util.Log
import com.ev.greenh.common.commondata.ApiIdentifier
import com.ev.greenh.common.commondata.RetrofitPool
import com.ev.greenh.common.commondata.api.PlantNetApi
import com.ev.greenh.plantidentify.data.model.req.PlantIdentifyReq
import com.ev.greenh.plantidentify.data.model.res.PlantIdentifyUIRes
import com.ev.greenh.repository.BaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import kotlinx.coroutines.tasks.await

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

class PlantIdentifyRepo : BaseRepository() {

    private val TAG = "PlantIdentificationRepo"

    private val functions = Firebase.functions

    private val firebaseStorage = Firebase.storage.reference

    private val api = RetrofitPool.getApi(ApiIdentifier.PlantNetApi).service as? PlantNetApi

    suspend fun uploadPlantToIdentify(
        fileName: String,
        image: Uri
    ) = safeApiCall {
        firebaseStorage.child(fileName).putFile(image).await()
    }

    suspend fun uploadPlantToIdentifyBytes(
        fileName: String,
        image: ByteArray
    ) = safeApiCall {
        firebaseStorage.child(fileName).putBytes(image).await()
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
        res?.let {
            if(res.isSuccessful) {
                res.body()?.bestMatch?.let {
                    return@safeApiCall PlantIdentifyUIRes(it)
                }
                return@safeApiCall null
            }
            return@safeApiCall null
        }
        return@safeApiCall null
    }

    fun callVision(base64encoded: String) {
        try {
            val request = JsonObject()
            val image = JsonObject()
            image.add("content", JsonPrimitive(base64encoded))
            request.add("image", image)
            val feature = JsonObject()
            feature.add("maxResults", JsonPrimitive(1))
            feature.add("type", JsonPrimitive("LABEL_DETECTION"))
            val features = JsonArray()
            features.add(feature)
            request.add("features", features)

            annotateImage(request.toString())
                ?.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        for (label in task.result!!.asJsonArray[0].asJsonObject["labelAnnotations"].asJsonArray) {
                            val labelObj = label.asJsonObject
                            val text = labelObj["description"]
                            val entityId = labelObj["mid"]
                            val confidence = labelObj["score"]
                        }
                    }
                }
        } catch (e: Exception) {
            Log.d(TAG, "Error: ${e.message}")
        }
    }

    private fun annotateImage(requestJson: String): Task<JsonElement>? {
        return try {
            functions
                .getHttpsCallable("annotateImage")
                .call(requestJson)
                .continueWith { task ->
                    val result = task.result?.data
                    JsonParser.parseString(Gson().toJson(result))
                }
        } catch (e: FirebaseFunctionsException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}