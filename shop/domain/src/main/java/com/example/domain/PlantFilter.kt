package com.example.domain

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

sealed class PlantFilter {
    data class Category(val category: PlantCategory) : PlantFilter()
    data class Availability(val availability: Boolean) : PlantFilter()
    data class Price(val startPrice: Int, val endPrice: Int) : PlantFilter()
}