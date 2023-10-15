package com.ev.greenh.plantidentification.data.model.res

import com.ev.greenh.plantidentification.data.model.common.BoundingPoly

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class Paragraph(
    val property: TextProperty,
    val boundingBox: BoundingPoly,
    val words: List<Word>,
    val confidence: Double
)