package com.example.admin.ui.shop.model

/*
 * Created by Sudhanshu Kumar on 28/11/24.
 */

data class PlantAdmin(
    val id: String? = null,
    val name: String,
    val des: String,
    val sunlight: String,
    val water: String,
    val availability: Boolean,
    val category: String,
    val imageUrl: String?,
    val videoUrl: String,
    val price: Long,
    val approxHeight: Long,
    val maintenance: String
)
