package com.ev.greenh.plantidentification.data.model.res

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class EntityAnnotation(
    val mid: String,
    val locale: String,
    val description: String,
    val score: Double,
    val confidence: Double, // Deprecated, use score instead
    val topicality: Double,
    val boundingPoly: BoundingPolyRes?,
    val locations: List<LocationInfo>,
    val properties: List<Property>
)