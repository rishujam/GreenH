package com.ev.greenh.common.commondata.plantidentifyres

import com.google.gson.annotations.SerializedName

data class Family(
    @SerializedName("scientificName")
    val scientificName: String,
    @SerializedName("scientificNameAuthorship")
    val scientificNameAuthorship: String,
    @SerializedName("scientificNameWithoutAuthor")
    val scientificNameWithoutAuthor: String
)