package com.ev.greenh.plantidentify.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.plantidentify.doamin.usecase.PlantIdentifyUseCase
import com.ev.greenh.plantidentify.ui.event.PlantIdentifyEvent
import com.ev.greenh.plantidentify.ui.model.IdentifyImage
import com.ev.greenh.plantidentify.ui.state.PlantIdentifyScreenState
import com.ev.greenh.plantidentify.ui.state.PlantIdentifyState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

class PlantIdentifyViewModel(
    private val useCase: PlantIdentifyUseCase
) : ViewModel() {

    var state by mutableStateOf(PlantIdentifyState())

    fun onEvent(event: PlantIdentifyEvent) {
        when (event) {
            is PlantIdentifyEvent.IdentifyClick -> {
                event.image.let {
                    state = state.copy(
                        image = event.image,
                        isLoading = true,
                        loadingText = "Uploading Image..."
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

    private fun identifyPlant(image: IdentifyImage) = viewModelScope.launch(Dispatchers.IO) {
        state = state.copy(
            loadingText = "Analyzing Image..."
        )
        val fileName = "/fileName"
        val response = useCase.invoke(fileName, image)
        response.error?.let {
            state = state.copy(
                isLoading = false,
                toast = it
            )
        } ?: run {
            state = state.copy(
                isLoading = false,
                result = response.names
            )
        }
    }

}