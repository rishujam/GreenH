package com.ev.greenh.plantidentify.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class TextProperty(
    val detectedLanguages: List<DetectedLanguage>,
    val detectedBreak: DetectedBreak
)
