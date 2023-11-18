package com.ev.greenh.plantidentify.data.model.req

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

data class PlantIdentifyReq(
    val imageUrls: List<String>,
    val organs: List<String>
)

