package com.ev.greenh.plantidentify.data.model.req

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class ImageContext(
    val latLongRect: LatLongRect?,
    val languageHints: List<String>?,
    val cropHintsParams: CropHintsParams?,
    val productSearchParams: ProductSearchParams?,
    val webDetectionParams: WebDetectionParams?,
    val textDetectionParams: TextDetectionParams?
)
