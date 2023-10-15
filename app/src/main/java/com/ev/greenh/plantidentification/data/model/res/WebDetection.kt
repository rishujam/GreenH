package com.ev.greenh.plantidentification.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class WebDetection(
    val webEntities: List<WebEntity>,
    val fullMatchingImages: List<WebImage>,
    val partialMatchingImages: List<WebImage>,
    val pagesWithMatchingImages: List<WebPage>,
    val visuallySimilarImages: List<WebImage>,
    val bestGuessLabels: List<WebLabel>
)