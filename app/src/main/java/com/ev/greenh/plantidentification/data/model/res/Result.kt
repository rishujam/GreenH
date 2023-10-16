package com.ev.greenh.plantidentification.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class Result(
    val product: Product,
    val score: Double,
    val image: String
)