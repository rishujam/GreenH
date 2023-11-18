package com.ev.greenh.plantidentify.ui.model

import android.graphics.Bitmap
import android.net.Uri

/*
 * Created by Sudhanshu Kumar on 04/11/23.
 */

sealed class IdentifyImage {

    data class UriImage(val uri: Uri) : IdentifyImage()

    data class BitmapImage(val bitmap: Bitmap) : IdentifyImage()

}
