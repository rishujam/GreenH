package com.example.domain.model

import java.io.Serializable

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

data class Plant(
    val id: String,
    val name: String,
    val des: String,
    val sunlight: Sunlight,
    val water: Water,
    val availability: Boolean,
    val category: PlantCategory,
    val imageUrl: String,
    val videoUrl: String,
    val price: Long,
    val approxHeight: Long,
    val maintenance: Maintenance
): Serializable
