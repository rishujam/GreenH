package com.example.domain.repo

import com.core.util.Resource
import com.example.domain.Plant
import com.example.domain.PlantFilter

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

interface PlantRepository {

    suspend fun getPlants(page: Int, filters: List<PlantFilter>?): Resource<List<Plant>>

}