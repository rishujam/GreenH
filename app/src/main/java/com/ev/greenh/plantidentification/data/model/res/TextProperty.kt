package com.ev.greenh.plantidentification.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class TextProperty(
    val detectedLanguages: List<DetectedLanguage>,
    val detectedBreak: DetectedBreak
)