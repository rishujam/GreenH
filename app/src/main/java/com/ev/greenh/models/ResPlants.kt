package com.ev.greenh.models

import com.ev.greenh.shop.data.model.ResPlant

data class ResPlants(
    val plants: List<ResPlant>,
    val error: String? = null,
    val isSuccessful: Boolean
)
