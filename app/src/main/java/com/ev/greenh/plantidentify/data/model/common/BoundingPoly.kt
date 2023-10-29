package com.ev.greenh.plantidentify.data.model.common

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class BoundingPoly(
    val vertices: List<Vertex>,
    val normalizedVertices: List<NormalizedVertex>
)
