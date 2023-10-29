package com.ev.greenh.plantidentify.ui

import android.net.Uri

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

sealed class PlantIdentifyEvent {

    data class IdentifyClick(val uri: Uri?) : PlantIdentifyEvent()

}