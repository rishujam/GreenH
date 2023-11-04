package com.ev.greenh.plantidentify.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.plantidentify.data.model.req.PlantIdentifyReq
import com.ev.greenh.plantidentify.data.repo.PlantIdentifyRepo
import com.ev.greenh.plantidentify.ui.model.IdentifyImage
import com.ev.greenh.plantidentify.ui.state.PlantIdentifyScreenState
import com.ev.greenh.plantidentify.ui.state.PlantIdentifyState
import com.ev.greenh.util.Resource
import com.ev.greenh.util.toByteArray
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

class PlantIdentifyViewModel(
    private val repo: PlantIdentifyRepo
) : ViewModel() {

    var state by mutableStateOf(PlantIdentifyState())

    fun onEvent(event: PlantIdentifyEvent) {
        when (event) {
            is PlantIdentifyEvent.IdentifyClick -> {
                event.image.let {
                    state = state.copy(
                        image = event.image
                    )
                    identifyPlant(event.image)
                }
            }

            is PlantIdentifyEvent.ImageSelected -> {
                state = state.copy(
                    currentScreen = PlantIdentifyScreenState.IdentifyScreen
                )
            }

            is PlantIdentifyEvent.BackClickFromResult -> {
                state = state.copy(
                    currentScreen = PlantIdentifyScreenState.CameraScreen,
                    result = null,
                    image = null
                )
            }
        }
    }

    private fun identifyPlant(image: IdentifyImage) = viewModelScope.launch {
        state = state.copy(
            isLoading = true
        )
        val fileName = "/fileName"
        when (image) {
            is IdentifyImage.BitmapImage -> {
                repo.uploadPlantToIdentifyBytes(fileName, image.bitmap.toByteArray())
            }

            is IdentifyImage.UriImage -> {
                repo.uploadPlantToIdentify(fileName, image.uri)
            }
        }
        when (val res = repo.getPublicUrlOfFileFirebase(fileName)) {
            is Resource.Success -> {
                val url = res.data
                Log.d("RishuTest", "url: $url")
                url?.let {
                    when (
                        val result =
                            repo.identifyPlant(PlantIdentifyReq(listOf(url), listOf("leaf")))
                    ) {
                        is Resource.Success -> {
                            Log.d("RishuTest", "msg: ${result.data?.name}")
                            state = state.copy(
                                isLoading = false,
                                result = result.data?.name
                            )
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                toast = result.message
                            )
                        }

                        is Resource.Loading -> {}
                    }
                }
            }

            is Resource.Error -> {}
            is Resource.Loading -> {}
        }
    }

}