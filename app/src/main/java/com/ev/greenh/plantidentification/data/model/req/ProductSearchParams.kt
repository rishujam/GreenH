package com.ev.greenh.plantidentification.data.model.req

import com.ev.greenh.plantidentification.data.model.common.BoundingPoly

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class ProductSearchParams(
    val boundingPoly: BoundingPoly?,
    val productSet: String,
    val productCategories: List<String>,
    val filter: String?
)

