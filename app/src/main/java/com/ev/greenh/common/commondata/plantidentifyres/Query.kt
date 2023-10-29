package com.ev.greenh.common.commondata.plantidentifyres

import com.google.gson.annotations.SerializedName

data class Query(
    val images: List<String>,
    @SerializedName("includeRelatedImages")
    val includeRelatedImages: Boolean,
    val organs: List<String>,
    val project: String
)