package com.ev.greenh.plantidentify.data.model.res

import com.google.gson.annotations.SerializedName

data class Query(
    val images: List<String>,
    @SerializedName("includeRelatedImages")
    val includeRelatedImages: Boolean,
    val organs: List<String>,
    val project: String
)