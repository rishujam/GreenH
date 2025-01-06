package com.example.domain.usecase

import com.core.util.Resource
import com.example.domain.model.GetPlantsRequest
import com.example.domain.model.Plant
import com.example.domain.model.PlantFilter
import com.example.domain.repo.PlantListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

class GetPlantListUseCase @Inject constructor(
    private val repo: PlantListRepository
) {

    operator fun invoke(
        req: GetPlantsRequest,
    ): Flow<Resource<List<Plant>>> = flow {
        emit(Resource.Loading())
        val result = repo.getPlants(req)
        emit(result)
    }

}