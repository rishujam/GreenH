package com.example.data.repo

import com.core.util.Resource
import com.example.data.mapper.MapperPlant
import com.example.data.source.FireStore
import com.example.domain.Plant
import com.example.domain.PlantFilter
import com.example.domain.repo.PlantRepository
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
    ): Resource<List<Plant>> {
        val result = firestore.getPlants(page, filters)
        return if(result.isSuccessful) {
            val plants = result.plants.map {
                MapperPlant.toPlant(it)
            }
            Resource.Success(plants)
        } else {
            Resource.Error(message = result.error)
        }
    }
}