package com.example.data.repo

import com.core.util.Resource
import com.example.data.mapper.MapperPlant
import com.example.data.remote.PlantDataSource
import com.example.domain.model.GetPlantsRequest
import com.example.domain.model.Plant
import com.example.domain.model.PlantFilter
import com.example.domain.repo.PlantListRepository
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

class PlantListRepositoryImpl @Inject constructor(
    private val source: PlantDataSource
) : PlantListRepository {

    override suspend fun getPlants(
        req: GetPlantsRequest
    ): Resource<List<Plant>> {
        val result = source.getPlants(
            req.page,
            req.filters
        )
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