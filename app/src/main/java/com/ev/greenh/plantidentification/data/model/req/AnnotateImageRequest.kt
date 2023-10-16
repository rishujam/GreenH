package com.ev.greenh.plantidentification.data.model.req

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class AnnotateImageRequest(
    val image: Image,
    val features: List<Feature>,
    val imageContext: ImageContext?
)
