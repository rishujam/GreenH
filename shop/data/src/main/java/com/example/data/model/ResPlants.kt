package com.example.data.model


data class ResPlants(
    val plants: List<ResPlant>,
    val error: String? = null,
    val isSuccessful: Boolean
)
