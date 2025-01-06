package com.example.domain.model

/*
 * Created by Sudhanshu Kumar on 26/12/24.
 */

data class GetPlantsRequest(
    val filters: List<PlantFilter>?,
    val page: Int
)
