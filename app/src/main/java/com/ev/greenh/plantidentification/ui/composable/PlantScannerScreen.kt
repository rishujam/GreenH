package com.ev.greenh.plantidentification.ui.composable

import android.content.Context
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ev.greenh.commonui.composable.CameraPreview

/*
 * Created by Sudhanshu Kumar on 22/10/23.
 */

@Composable
fun PlantScannerScreen(applicationContext: Context) {
    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
//            setImageAnalysisAnalyzer(
//                ContextCompat.getMainExecutor(applicationContext),
//                analyzer
//            )
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(controller = controller, Modifier.fillMaxSize())
    }
}