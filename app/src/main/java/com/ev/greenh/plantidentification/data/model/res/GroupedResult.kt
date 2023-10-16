package com.ev.greenh.plantidentification.data.model.res

import com.ev.greenh.plantidentification.data.model.common.BoundingPoly

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class GroupedResult(
    val boundingPoly: BoundingPoly,
    val results: List<Result>,
    val objectAnnotations: List<ObjectAnnotation>
)
