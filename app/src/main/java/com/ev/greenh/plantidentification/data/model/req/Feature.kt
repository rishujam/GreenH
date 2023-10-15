package com.ev.greenh.plantidentification.data.model.req

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

data class Feature(
    val type: Type,
    val maxResults: Int,
    val model: String?
) {
    enum class Type {
        TYPE_UNSPECIFIED,
        FACE_DETECTION,
        LANDMARK_DETECTION,
        LOGO_DETECTION,
        LABEL_DETECTION,
        TEXT_DETECTION,
        DOCUMENT_TEXT_DETECTION,
        SAFE_SEARCH_DETECTION,
        IMAGE_PROPERTIES,
        CROP_HINTS,
        WEB_DETECTION,
        PRODUCT_SEARCH,
        OBJECT_LOCALIZATION
    }
}