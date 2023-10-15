package com.ev.greenh.plantidentification.data.model.res

import com.ev.greenh.plantidentification.data.model.common.BoundingPoly

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class Word(
    val property: TextProperty,
    val boundingBox: BoundingPoly,
    val symbols: List<Symbol>,
    val confidence: Double
)