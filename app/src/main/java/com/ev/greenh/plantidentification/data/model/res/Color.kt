package com.ev.greenh.plantidentification.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class Color(
    val red: Double,   // The amount of red in the color as a value in the interval [0, 1].
    val green: Double, // The amount of green in the color as a value in the interval [0, 1].
    val blue: Double,  // The amount of blue in the color as a value in the interval [0, 1].
    val alpha: Double? // The fraction of this color that should be applied to the pixel. Can be null.
)