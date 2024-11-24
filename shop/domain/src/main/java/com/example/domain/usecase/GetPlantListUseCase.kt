package com.example.domain.usecase

import com.core.util.Resource
import com.example.domain.Plant
import com.example.domain.PlantFilter
import com.example.domain.repo.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

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
        emit(result)
    }

}