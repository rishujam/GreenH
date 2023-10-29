package com.ev.greenh.plantidentify.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class Page(
    val property: TextProperty,
    val width: Int,
    val height: Int,
    val blocks: List<Block>,
    val confidence: Double
)
