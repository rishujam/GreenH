package com.example.data.source

import com.core.util.Constants
import com.example.data.model.ResPlant
import com.example.data.model.ResPlants
import com.example.domain.PlantFilter
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

class FireStore @Inject constructor() {

    private val fireRef = Firebase.firestore

    suspend fun getPlants(page: Int, filters: List<PlantFilter>?): ResPlants {
        val plants = mutableListOf<ResPlant>()
        val startAfter = Constants.QUERY_PAGE_SIZE * (page - 1)
        val query = fireRef.collection(Constants.FirebaseColl.PLANTS)
            .startAfter(startAfter)
            .limit(Constants.QUERY_PAGE_SIZE.toLong())
        filters?.let {
            for(i in filters) {
                when(i) {
                    is PlantFilter.Price -> {
                        query.where(Filter.greaterThanOrEqualTo("price", i.startPrice))
                        query.where(Filter.lessThan("price", i.endPrice))
                    }

                    is PlantFilter.Category -> {
                        query.where(Filter.equalTo("category", i.category.value))
                    }

                    is PlantFilter.Availability -> {
                        query.where(Filter.equalTo("availability", i.availability))
                    }
                }
            }
        }
        return try {
            val docs = query.get().await()
            for (i in docs.documents) {
                val plant = i.toObject<ResPlant>()
                plant?.let { plants.add(it) }
            }
            ResPlants(
                plants,
                null,
                true
            )
        } catch (e: Exception) {
            ResPlants(
                emptyList(),
                "Error: ${e.message}",
                false
            )
        }
    }

}