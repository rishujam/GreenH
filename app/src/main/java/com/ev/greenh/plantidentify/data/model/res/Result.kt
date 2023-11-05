package com.ev.greenh.plantidentify.data.model.res

data class Result(
    val gbif: Gbif,
    val images: List<Image>,
    val iucn: Iucn,
    val powo: Powo,
    val score: Float,
    val species: Species
)