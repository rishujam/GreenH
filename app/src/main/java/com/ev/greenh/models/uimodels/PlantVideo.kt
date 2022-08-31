package com.ev.greenh.models.uimodels

import android.net.Uri

data class PlantVideo(
    private val video:String
) {
    fun getVideoUri(): Uri {
        return Uri.parse(video)
    }
}