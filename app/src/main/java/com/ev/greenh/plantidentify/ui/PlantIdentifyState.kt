package com.ev.greenh.plantidentify.ui

import android.net.Uri

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

data class PlantIdentifyState(
    val imageUri: Uri? = null,
    val toast: String? = null
)