package com.ev.greenh.plantidentify.ui.state

import android.net.Uri
import com.ev.greenh.plantidentify.ui.model.IdentifyImage

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

data class PlantIdentifyState(
    val image: IdentifyImage? = null,
    val toast: String? = null,
    val currentScreen: PlantIdentifyScreenState = PlantIdentifyScreenState.CameraScreen,
    val result: List<String>? = null,
    val isLoading: Boolean = false,
    val loadingText: String = ""
)