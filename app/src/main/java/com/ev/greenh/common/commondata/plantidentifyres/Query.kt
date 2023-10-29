package com.ev.greenh.common.commondata.plantidentifyres

data class Query(
    val images: List<String>,
    val includeRelatedImages: Boolean,
    val organs: List<String>,
    val project: String
)