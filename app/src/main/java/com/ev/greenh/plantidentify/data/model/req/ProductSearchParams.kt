package com.ev.greenh.plantidentify.data.model.req

import com.ev.greenh.plantidentify.data.model.common.BoundingPoly

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class ProductSearchParams(
    val boundingPoly: BoundingPoly?,
    val productSet: String,
    val productCategories: List<String>,
    val filter: String?
)

