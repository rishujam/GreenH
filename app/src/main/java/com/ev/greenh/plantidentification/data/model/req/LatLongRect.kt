package com.ev.greenh.plantidentification.data.model.req

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class LatLongRect(
    val minLatLng: LatLng,
    val maxLatLng: LatLng
)