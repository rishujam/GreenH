package com.core.data.model

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

data class ResFeatureConfig(
    val success: Boolean,
    val message: String? = null,
    val featureList: List<Feature>? = null
)