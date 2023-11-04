package com.ev.greenh.plantidentify.ui.state

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

sealed class PlantIdentifyScreenState {

    object CameraScreen : PlantIdentifyScreenState()

    object IdentifyScreen : PlantIdentifyScreenState()

}