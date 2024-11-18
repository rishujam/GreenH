package com.ev.greenh.shop.data.repo

import com.ev.greenh.models.ResPlants
import com.ev.greenh.shop.data.source.FireStore
import com.ev.greenh.shop.domain.PlantFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

class PlantRepositoryImpl @Inject constructor(
    private val firestore: FireStore
) : PlantRepository {

    override suspend fun getPlants(
        page: Int,
        filters: List<PlantFilter>?
    ): ResPlants {
        withContext(Dispatchers.IO) {
            return@withContext firestore.getPlants(page, filters)
        }
        return ResPlants(
            emptyList(),
            null,
            true
        )
    }
}