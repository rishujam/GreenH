package com.ev.greenh.plantidentify.ui

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.plantidentify.data.PlantIdentifyRepo
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

class PlantIdentifyViewModel(
    private val repo: PlantIdentifyRepo
) : ViewModel() {

    var state by mutableStateOf(PlantIdentifyState())

    fun onEvent(event: PlantIdentifyEvent) {
        when(event) {
            is PlantIdentifyEvent.IdentifyClick -> {
                event.uri?.let {
                    state = state.copy(
                        imageUri = event.uri
                    )
                    identifyPlant(event.uri)
                } ?: {
                    state = state.copy(toast = "Please select a valid images")
                }
            }
        }
    }

    private fun identifyPlant(uri: Uri) = viewModelScope.launch {
        repo.uploadPlantToIdentify(uri)
    }

}