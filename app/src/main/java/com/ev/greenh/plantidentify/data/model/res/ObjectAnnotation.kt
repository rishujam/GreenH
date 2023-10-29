package com.ev.greenh.plantidentify.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */


data class ObjectAnnotation(
    val mid: String,
    val languageCode: String,
    val name: String,
    val score: Double
)