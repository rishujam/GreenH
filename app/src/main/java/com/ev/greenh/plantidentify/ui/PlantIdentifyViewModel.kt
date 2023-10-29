package com.ev.greenh.plantidentify.ui

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.plantidentify.data.model.req.PlantIdentifyReq
import com.ev.greenh.plantidentify.data.repo.PlantIdentifyRepo
import com.ev.greenh.util.Resource
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

    fun identifyPlant(uri: Uri) = viewModelScope.launch {
        val fileName = "/fileName"
        repo.uploadPlantToIdentify(fileName, uri)
        val res = repo.getPublicUrlOfFileFirebase(fileName)
        when(res) {
            is Resource.Success -> {
                val url = res.data
                url?.let {
                    Log.d("RishuTest", "url: $it")
                    val result = repo.identifyPlant(PlantIdentifyReq(listOf(url), listOf("leaf")))
                    when(result) {
                        is Resource.Success -> {
                            Log.d("RishuTest", result.data?.name.toString())
                        }
                        is Resource.Error -> {
                            Log.d("RishuTest", result.message.toString())
                        }
                        is Resource.Loading -> {}
                    }

                }
            }
            is Resource.Error -> {  }
            is Resource.Loading -> {    }
        }
    }

}