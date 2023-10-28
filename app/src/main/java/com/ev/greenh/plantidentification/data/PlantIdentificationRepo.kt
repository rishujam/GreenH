package com.ev.greenh.plantidentification.data

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

class PlantIdentificationRepo {

    private val functions = Firebase.functions

    fun request(base64encoded: String) {
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
                        Log.d("RishuTest", "isSuccessful: ${task.result}")
                        for (label in task.result!!.asJsonArray[0].asJsonObject["labelAnnotations"].asJsonArray) {
                            val labelObj = label.asJsonObject
                            val text = labelObj["description"]
                            val entityId = labelObj["mid"]
                            val confidence = labelObj["score"]
                            Log.d("RishuTest", "text: $text, confidence: $confidence")
                        }
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
}