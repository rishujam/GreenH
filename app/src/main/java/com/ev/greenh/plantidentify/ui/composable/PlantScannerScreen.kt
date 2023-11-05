package com.ev.greenh.plantidentify.ui.composable

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ev.greenh.R
import com.ev.greenh.common.commonui.Mat3Bg
import com.ev.greenh.common.commonui.Mat3OnBg
import com.ev.greenh.common.commonui.NunitoFontFamily
import com.ev.greenh.common.commonui.composable.CameraPreview
import com.ev.greenh.common.commonui.composable.GButton
import com.ev.greenh.common.commonui.composable.LoadingAnimation
import com.ev.greenh.common.commonui.composable.Toolbar
import com.ev.greenh.plantidentify.ui.event.PlantIdentifyEvent
import com.ev.greenh.plantidentify.ui.PlantIdentifyViewModel
import com.ev.greenh.plantidentify.ui.model.IdentifyImage
import com.ev.greenh.plantidentify.ui.state.PlantIdentifyScreenState
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.findActivity

/*
 * Created by Sudhanshu Kumar on 22/10/23.
 */

@Composable
fun PlantScannerScreen(
    viewModel: PlantIdentifyViewModel,
    applicationContext: Context
) {
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    var selectedImage by remember {
        mutableStateOf<IdentifyImage?>(null)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                selectedImage = IdentifyImage.UriImage(uri)
                viewModel.onEvent(PlantIdentifyEvent.ImageSelected)
            }
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = viewModel.state.currentScreen == PlantIdentifyScreenState.CameraScreen,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CameraPreview(
                    controller = controller,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .padding(start = 32.dp, bottom = 32.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Mat3OnBg)
                            .padding(8.dp)
                            .clickable {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        painter = painterResource(id = R.drawable.ic_gallery),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Mat3Bg)
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .align(Alignment.BottomCenter)
                        .clickable {
                            val activity = context.findActivity()
                            (activity as? MainActivity)?.takePhoto(controller) {
                                selectedImage = IdentifyImage.BitmapImage(it)
                                viewModel.onEvent(PlantIdentifyEvent.ImageSelected)
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Mat3Bg)
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = viewModel.state.currentScreen == PlantIdentifyScreenState.IdentifyScreen,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when(selectedImage) {
                    is IdentifyImage.UriImage -> {
                        AsyncImage(
                            model = (selectedImage as IdentifyImage.UriImage).uri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    is IdentifyImage.BitmapImage -> {
                        AsyncImage(
                            model = (selectedImage as IdentifyImage.BitmapImage).bitmap,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else -> {}
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    AnimatedVisibility(
                        visible = viewModel.state.result == null,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        GButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "Identify",
                            isEnabled = true
                        ) {
                            selectedImage?.let {
                                viewModel.onEvent(
                                    PlantIdentifyEvent.IdentifyClick(
                                        it
                                    )
                                )
                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = viewModel.state.result != null,
                        enter = slideInVertically() + fadeIn(),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            val text = viewModel.state.result ?: listOf("No result found")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Mat3Bg)
                                    .padding(vertical = 16.dp),
                                text = text[0],
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                fontFamily = NunitoFontFamily,
                                textAlign = TextAlign.Center,
                                color = Mat3OnBg
                            )
                        }
                    }
                }
            }
        }
        Toolbar(
            title = "Plant Identify",
            icon = R.drawable.back_btn,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            when(viewModel.state.currentScreen) {
                is PlantIdentifyScreenState.IdentifyScreen -> {
                    viewModel.onEvent(PlantIdentifyEvent.BackClickFromResult)
                }
                is PlantIdentifyScreenState.CameraScreen -> {

                }
            }
        }
        AnimatedVisibility(
            visible = viewModel.state.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                LoadingAnimation()
            }
        }
    }
}