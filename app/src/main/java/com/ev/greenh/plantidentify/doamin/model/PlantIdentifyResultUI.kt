package com.ev.greenh.plantidentify.doamin.model

/*
 * Created by Sudhanshu Kumar on 05/11/23.
 */

data class PlantIdentifyResultUI(
    val names: List<String>,
    val error: String? = null
)