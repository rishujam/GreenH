package com.ev.greenh.common.commondata.plantidentifyres

data class Species(
    val commonNames: List<String>,
    val family: Family,
    val genus: Genus,
    val scientificName: String,
    val scientificNameAuthorship: String,
    val scientificNameWithoutAuthor: String
)