package com.ev.greenh.shop.domain.usecase

import com.core.util.Resource
import com.ev.greenh.shop.data.repo.PlantRepository
import com.ev.greenh.shop.domain.Plant
import com.ev.greenh.shop.domain.PlantFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

class GetPlantListUseCase @Inject constructor(
    private val repo: PlantRepository
) {

    operator fun invoke(
        page: Int,
        filters: List<PlantFilter>? = null
    ): Flow<Resource<List<Plant>>> = flow {
        emit(Resource.Loading())
        val result = repo.getPlants(page = page, filters = filters)
        if(result.isSuccessful) {
            val plants = result.plants.map { plantRes ->
                plantRes.toPlant()
            }
            emit(Resource.Success(plants))
        } else {
            emit(Resource.Error(result.error))
        }
    }

}