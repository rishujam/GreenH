package com.ev.greenh.shop.data.model

import com.ev.greenh.shop.domain.Maintenance
import com.ev.greenh.shop.domain.Plant
import com.ev.greenh.shop.domain.PlantCategory
import com.ev.greenh.shop.domain.Sunlight
import com.ev.greenh.shop.domain.Water

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
    val price: Int,
    val approxHeight: Int,
    val maintenance: String
) {

    fun toPlant(): Plant {
        return Plant(
            id = id,
            name = name,
            des = des,
            sunlight = Sunlight.fromValue(sunlight),
            water = Water.fromValue(water),
            availability = availability,
            category = PlantCategory.fromValue(category),
            imageUrl = imageUrl,
            videoUrl = videoUrl,
            price = price,
            approxHeight = approxHeight,
            maintenance = Maintenance.fromValue(maintenance)
        )
    }

}