package com.ev.greenh.plantidentify.data.model.res

import com.ev.greenh.plantidentify.data.model.common.BoundingPoly

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class LocalizedObjectAnnotation(
    val mid: String,
    val languageCode: String,
    val name: String,
    val score: Double,
    val boundingPoly: BoundingPoly
)
