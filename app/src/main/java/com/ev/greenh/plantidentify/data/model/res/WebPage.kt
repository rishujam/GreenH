package com.ev.greenh.plantidentify.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class WebPage(
    val url: String,
    val score: Double,
    val pageTitle: String,
    val fullMatchingImages: List<WebImage>,
    val partialMatchingImages: List<WebImage>
)