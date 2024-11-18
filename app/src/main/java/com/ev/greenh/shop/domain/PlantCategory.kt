package com.ev.greenh.shop.domain

import com.ev.greenh.shop.domain.Water.EMPTY

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

enum class PlantCategory(val value: String) {
    INDOOR("Indoor"),
    OUTDOOR("Outdoor"),
    EMPTY("");

    companion object {
        fun fromValue(value: String): PlantCategory {
            return PlantCategory.values().find { it.value.equals(value, ignoreCase = false) }
                ?: EMPTY
        }
    }
}