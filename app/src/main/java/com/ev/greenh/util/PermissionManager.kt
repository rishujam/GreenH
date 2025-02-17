package com.ev.greenh.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

/*
 * Created by Sudhanshu Kumar on 28/01/25.
 */

object PermissionManager {

    fun launch(
        permissionType: PermissionType,
        context: Context,
        permissionLauncher: ActivityResultLauncher<Array<String>>,
        onPermissionGranted: (granted: Boolean) -> Unit
    ) {
        val permissionsToAsk = getPermissionsToAsk(permissionType, context)
        if (permissionsToAsk.isEmpty()) {
            onPermissionGranted(true)
        } else {
            permissionLauncher.launch(permissionsToAsk.toTypedArray())
        }
    }

    fun evaluateResult(
        results: Map<String, Boolean>,
        onUserActionPerformed: ((absResult: Boolean) -> Unit)
    ) {
        results.forEach { (_, value) ->
            if(!value) {
                onUserActionPerformed(false)
                return
            }
        }
        onUserActionPerformed(true)
    }

    fun checkGranted(
        permissionKey: String,
        context: Context
    ): Boolean = ContextCompat.checkSelfPermission(
        context,
        permissionKey
    ) != PackageManager.PERMISSION_DENIED

    private fun getPermissionsToAsk(
        permissionType: PermissionType,
        context: Context
    ): List<String> {
        val permissionsToAsk = mutableListOf<String>()
        when(permissionType) {
            PermissionType.CAMERA -> {
                val cameraPermissionKey = Manifest.permission.CAMERA
                if(!checkGranted(cameraPermissionKey, context)) {
                    permissionsToAsk.add(cameraPermissionKey)
                }
            }
            PermissionType.NOTIFICATION -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val notificationPermissionKey = Manifest.permission.POST_NOTIFICATIONS
                    if(!checkGranted(notificationPermissionKey, context)) {
                        permissionsToAsk.add(notificationPermissionKey)
                    }
                }
            }
        }
        return permissionsToAsk
    }

}