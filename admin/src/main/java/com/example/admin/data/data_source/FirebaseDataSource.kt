package com.example.admin.data.data_source

import com.core.util.toObject
import com.example.admin.data.model.DataSourceRes
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

/*
 * Created by Sudhanshu Kumar on 24/11/24.
 */

class FirebaseDataSource {

    private val fireRef = Firebase.firestore
    private val fireStorage = Firebase.storage.reference

    suspend fun searchItem(
        collection: String,
        field: String,
        term: String
    ): List<Map<String, Any>> {
        val list = mutableListOf<Map<String, Any>>()
        val docs = fireRef.collection(collection).where(Filter.equalTo(field, term)).get().await()
        for(doc in docs) {
            list.add(doc.data)
        }
        return list
    }

    suspend fun uploadFileAndGetUrl(
        collection: String,
        fileName: String,
        image: ByteArray
    ): DataSourceRes {
        return try {
            fireStorage.child(collection).child(fileName).putBytes(image).await()
            val url = fireStorage.child("$collection/$fileName").downloadUrl.await().toString()
            DataSourceRes(
                isSuccessful = true,
                data = url
            )
        } catch (e: Exception) {
            DataSourceRes(
                isSuccessful = false,
                message = e.message
            )
        }
    }

    suspend fun <T> saveModel(
        collection: String,
        docId: String,
        model: T
    ): DataSourceRes {
        return try {
            fireRef.collection(collection)
                .document(docId).set(model as Any).await()
            DataSourceRes(
                isSuccessful = true,
                data = docId
            )
        } catch (e: Exception) {
            DataSourceRes(
                isSuccessful = false,
                message = e.message
            )
        }
    }

    suspend fun getModel(
        collection: String,
        docId: String
    ): Map<String, Any>? {
        return try {
            fireRef.collection(collection).document(docId).get().await().data
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Location is where the last generated id value stored
     * In format Collection/DocumentId/Field
     */
    suspend fun generateNewId(location: String): Long? {
        try {
            val collection = location.split("/")[0]
            val docId = location.split("/")[1]
            val field = location.split("/")[2]
            val doc = fireRef.collection(collection).document(docId).get().await()
            (doc[field] as? Long)?.let {
                val newId = it + 1
                fireRef.collection(collection).document(docId).update(field, newId).await()
                return newId
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }

}