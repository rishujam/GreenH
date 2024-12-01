package com.core.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContextCompat

/*
 * Created by Sudhanshu Kumar on 29/11/24.
 */

object IntentOpener {

    fun openImagePicker(
        context: Context?,
        requestPermission: ((permission: String) -> Unit)? = null,
        onLaunch: (intent: Intent) -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onLaunch(getImagePickerIntent())
        } else {
            context?.let {
                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission?.let { it(android.Manifest.permission.READ_EXTERNAL_STORAGE) }
                } else {
                    onLaunch(getImagePickerIntent())
                }
            }
        }
    }

    private fun getImagePickerIntent(): Intent {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        return intent
    }

}