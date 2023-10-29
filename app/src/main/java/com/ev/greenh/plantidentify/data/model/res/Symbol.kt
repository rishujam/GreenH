package com.ev.greenh.plantidentify.data.model.res

import com.ev.greenh.plantidentify.data.model.common.BoundingPoly

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class Symbol(
    val property: TextProperty,
    val boundingBox: BoundingPoly,
    val text: String,
    val confidence: Double
)