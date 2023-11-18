package com.ev.greenh.plantidentify.ui.event

import com.ev.greenh.plantidentify.ui.model.IdentifyImage

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

sealed class PlantIdentifyEvent {

    data class IdentifyClick(
        val image: IdentifyImage,
        val fileName: String
    ) : PlantIdentifyEvent()

    object ImageSelected : PlantIdentifyEvent()

    object BackClickFromResult : PlantIdentifyEvent()

}