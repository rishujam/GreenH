package com.ev.greenh.plantidentify.ui

import android.content.Context
import android.widget.ImageView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.analytics.AnalyticsRepo
import com.ev.greenh.home.data.Analytic
import com.ev.greenh.plantidentify.doamin.usecase.PlantIdentifyUseCase
import com.ev.greenh.plantidentify.ui.event.PlantIdentifyEvent
import com.ev.greenh.plantidentify.ui.model.IdentifyImage
import com.ev.greenh.plantidentify.ui.state.PlantIdentifyScreenState
import com.ev.greenh.plantidentify.ui.state.PlantIdentifyState
import com.ev.greenh.util.Constants
import com.ev.greenh.util.ImageCompressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

class PlantIdentifyViewModel(
    private val useCase: PlantIdentifyUseCase,
    private val analyticRepo: AnalyticsRepo
) : ViewModel() {

    var state by mutableStateOf(PlantIdentifyState())

    val list = mutableListOf<String>()

    fun onEvent(event: PlantIdentifyEvent) {
        when (event) {
            is PlantIdentifyEvent.IdentifyClick -> {
                event.image.let {
                    state = state.copy(
                        image = event.image,
                        isLoading = true,
                        loadingText = "Analyzing Image..."
                    )
                    identifyPlant(event.fileName, event.image)
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

    private fun compressImage(
        uri: String,
        context: Context,
        imageView: ImageView
    ) {

    }

    private fun identifyPlant(
        fileName: String,
        image: IdentifyImage
    ) = viewModelScope.launch(Dispatchers.IO) {
        val response = useCase.invoke(fileName, image)
        response.error?.let {
            state = state.copy(
                isLoading = false,
                toast = it
            )
            sendAnalytic()
        } ?: run {
            state = state.copy(
                isLoading = false,
                result = response.names
            )
        }
    }

    private fun sendAnalytic() = viewModelScope.launch(Dispatchers.IO) {
        val id = System.currentTimeMillis().toString()
        val analytic = Analytic(
            id,
            Constants.Feature.IDENTIFY,
            "error identify"
        )
        analyticRepo.postAnalytics(analytic)
    }

}