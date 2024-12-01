package com.example.admin.data.repo

import com.example.admin.data.model.DataSourceRes

/*
 * Created by Sudhanshu Kumar on 25/11/24.
 */

interface AdminRepository {

    suspend fun <T> saveModel(
        collection: String,
        docId: String,
        model: T
    ): DataSourceRes

    suspend fun getModel(
        collection: String,
        docId: String
    ): Map<String, Any>?

    suspend fun uploadFileAndGetUrl(
        collection: String,
        fileName: String,
        image: ByteArray
    ): DataSourceRes

    suspend fun generateNewId(
        location: String
    ): Long?

    suspend fun searchTerm(
        collection: String,
        field: String,
        term: String
    ): Map<String, Any>?

}