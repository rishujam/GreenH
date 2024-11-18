package com.ev.greenh.shop.data.repo

import com.ev.greenh.models.ResPlants
import com.ev.greenh.shop.domain.PlantFilter

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

interface PlantRepository {

    suspend fun getPlants(page: Int, filters: List<PlantFilter>?): ResPlants

}