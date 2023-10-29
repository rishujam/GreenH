package com.ev.greenh.common.commondata.plantidentifyres

import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("commonNames")
    val commonNames: List<String>,
    val family: Family,
    val genus: Genus,
    @SerializedName("scientificName")
    val scientificName: String,
    @SerializedName("scientificNameAuthorship")
    val scientificNameAuthorship: String,
    @SerializedName("scientificNameWithoutAuthor")
    val scientificNameWithoutAuthor: String
)