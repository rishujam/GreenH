package com.ev.greenh.plantidentify.data.model.res

import com.ev.greenh.plantidentify.data.model.res.Date
import com.ev.greenh.plantidentify.data.model.res.Url

data class Image(
    val author: String,
    val citation: String,
    val date: Date,
    val license: String,
    val organ: String,
    val url: Url
)