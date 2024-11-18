package com.ev.greenh.shop.domain

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
    val price: Int,
    val approxHeight: Int,
    val maintenance: Maintenance
)
