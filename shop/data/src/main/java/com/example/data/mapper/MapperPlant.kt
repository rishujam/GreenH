package com.example.data.mapper

import com.example.data.model.ResPlant
import com.example.domain.Maintenance
import com.example.domain.Plant
import com.example.domain.PlantCategory
import com.example.domain.Sunlight
import com.example.domain.Water

/*
 * Created by Sudhanshu Kumar on 21/11/24.
 */

object MapperPlant {

    fun toPlant(resPlant: ResPlant): Plant {
        resPlant.apply {
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

}