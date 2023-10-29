package com.ev.greenh.common.commondata.plantidentifyres

data class Result(
    val gbif: Gbif,
    val images: List<Image>,
    val iucn: Iucn,
    val powo: Powo,
    val score: Int,
    val species: Species
)