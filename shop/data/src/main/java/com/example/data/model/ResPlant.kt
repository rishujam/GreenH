package com.example.data.model

data class ResPlant(
    val id: String,
    val name: String,
    val des: String,
    val sunlight: String,
    val water: String,
    val availability: Boolean,
    val category: String,
    val imageUrl: String,
    val videoUrl: String,
    val price: Long,
    val approxHeight: Long,
    val maintenance: String
)