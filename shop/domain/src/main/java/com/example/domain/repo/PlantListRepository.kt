package com.example.domain.repo

import com.core.util.Resource
import com.example.domain.model.GetPlantsRequest
import com.example.domain.model.Plant
import com.example.domain.model.PlantFilter

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

interface PlantListRepository {

    suspend fun getPlants(req: GetPlantsRequest): Resource<List<Plant>>

}