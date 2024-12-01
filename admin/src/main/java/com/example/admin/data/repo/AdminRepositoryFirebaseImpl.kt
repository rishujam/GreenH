package com.example.admin.data.repo

import com.example.admin.data.data_source.FirebaseDataSource
import com.example.admin.data.model.DataSourceRes

/*
 * Created by Sudhanshu Kumar on 25/11/24.
 */

class AdminRepositoryFirebaseImpl(
    private val firebaseDataSource: FirebaseDataSource
) : AdminRepository {

    override suspend fun <T> saveModel(
        collection: String,
        docId: String,
        model: T
    ): DataSourceRes {
        return firebaseDataSource.saveModel(
            collection, docId, model
        )
    }

    override suspend fun getModel(collection: String, docId: String): Map<String, Any>? {
        return firebaseDataSource.getModel(
            collection,
            docId
        )
    }

    override suspend fun uploadFileAndGetUrl(
        collection: String,
        fileName: String,
        image: ByteArray
    ): DataSourceRes {
        return firebaseDataSource.uploadFileAndGetUrl(
            collection,
            fileName,
            image
        )
    }

    override suspend fun generateNewId(location: String): Long? {
        return firebaseDataSource.generateNewId(location)
    }

    override suspend fun searchTerm(
        collection: String,
        field: String,
        term: String
    ): Map<String, Any>? {
        return firebaseDataSource.searchItem(
            collection, field, term
        ).getOrNull(0)
    }
}