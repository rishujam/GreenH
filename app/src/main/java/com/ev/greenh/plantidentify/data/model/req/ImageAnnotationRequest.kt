package com.ev.greenh.plantidentify.data.model.req

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class ImageAnnotationRequest(
    val requests: List<AnnotateImageRequest>,
    val parent: String?
)