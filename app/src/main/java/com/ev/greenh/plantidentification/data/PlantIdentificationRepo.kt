package com.ev.greenh.plantidentification.data

import android.util.Log
import com.ev.greenh.plantidentification.data.model.req.ImageAnnotationRequest
import com.ev.greenh.plantidentification.data.model.res.AnnotateImageResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

class PlantIdentificationRepo {

    private val functions = FirebaseFunctions.getInstance()

    fun request(base64encoded: String) {
        try {
            val request = JsonObject()
            val image = JsonObject()
            image.add("content", JsonPrimitive(base64encoded))
            request.add("image", image)
            val feature = JsonObject()
            feature.add("maxResults", JsonPrimitive(5))
            feature.add("type", JsonPrimitive("LABEL_DETECTION"))
            val features = JsonArray()
            features.add(feature)
            request.add("features", features)

            annotateImage(request.toString())
                ?.addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.d("RishuTest", "isSuccessful: ${task.result}")
                    } else {
                        Log.d("RishuTest", "Failed: ${task.exception?.message}")
                    }
                }
        } catch (e: Exception) {
            Log.d("RishuTest", "Error: ${e.message}")
        }
    }

    private fun annotateImage(requestJson: String): Task<JsonElement>? {
        return try {
            functions
                .getHttpsCallable("annotateImage")
                .call(requestJson)
                .continueWith { task ->
                    val result = task.result?.data
                    Log.d("RishuTest", "${result}")
                    JsonParser.parseString(Gson().toJson(result))
                }
        } catch (e: FirebaseFunctionsException) {
            Log.d("RishuTest", "Exception: ${e.message}")
            null
        } catch (e: Exception) {
            Log.d("RishuTest", "Eoorr")
            null
        }

    }

    fun callApi(req: ImageAnnotationRequest) {
        VisionApi.visionApiService.annotateImages(req).enqueue(object : Callback<AnnotateImageResponse> {
            override fun onResponse(
                call: Call<AnnotateImageResponse>,
                response: Response<AnnotateImageResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d("RishuTest" ,"${response.message()}")
                    Log.d("RishuTest", "${response.body()}")
                } else {
                    Log.d("RishuTest" ,"${response.errorBody()?.byteString()}")
                    Log.d("RishuTest" ,"${response.code()}")
                }

            }

            override fun onFailure(call: Call<AnnotateImageResponse>, t: Throwable) {
                Log.d("RishuTest", "${t.message}")
            }
        })
    }
}