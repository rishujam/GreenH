package com.ev.greenh.plantidentify.doamin.usecase

import com.core.ui.toByteArray
import com.core.util.Resource
import com.ev.greenh.plantidentify.data.model.req.PlantIdentifyReq
import com.ev.greenh.plantidentify.data.repo.PlantIdentifyRepo
import com.ev.greenh.plantidentify.doamin.model.PlantIdentifyResultUI
import com.ev.greenh.plantidentify.ui.model.IdentifyImage

/*
 * Created by Sudhanshu Kumar on 05/11/23.
 */

class PlantIdentifyUseCase(
    private val repo: PlantIdentifyRepo
) {

    //TODO Convert this to flow and emit status of every step
    suspend operator fun invoke(fileName: String, image: IdentifyImage): PlantIdentifyResultUI {
        if (uploadFile(fileName, image)) {
            val url = getPublicUrl(fileName)
            url?.let {
                val response = identifyPlant(url)
                return PlantIdentifyResultUI(response)
            }
        }
        return PlantIdentifyResultUI(emptyList(), "Error while uploading file")
    }

    private suspend fun uploadFile(fileName: String, image: IdentifyImage): Boolean {
        val response = when (image) {
            is IdentifyImage.BitmapImage -> {
                repo.uploadPlantToIdentifyBytes(fileName, image.bitmap.toByteArray())
            }

            is IdentifyImage.UriImage -> {
                repo.uploadPlantToIdentify(fileName, image.uri)
            }
        }
        if (response is Resource.Success) {
            return true
        }
        return false
    }

    private suspend fun getPublicUrl(location: String): String? {
        val response = repo.getPublicUrlOfFileFirebase(location)
        if (response is Resource.Success) {
            return response.data
        }
        return null
    }

    private suspend fun identifyPlant(url: String): List<String> {
        val response = repo.identifyPlant(PlantIdentifyReq(listOf(url), listOf("leaf")))
        if (response is Resource.Success) {
            response.data?.results?.getOrNull(0)?.species?.commonNames?.let {
                return it
            } ?: run {
                return emptyList()
            }
        }
        return emptyList()
    }

}