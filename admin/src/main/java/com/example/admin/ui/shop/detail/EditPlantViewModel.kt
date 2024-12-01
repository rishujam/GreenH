package com.example.admin.ui.shop.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Constants
import com.core.util.Resource
import com.core.util.toObject
import com.example.admin.data.repo.AdminRepository
import com.example.admin.ui.shop.model.PlantAdmin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 28/11/24.
 */

class EditPlantViewModel(
    private val repo: AdminRepository
) : ViewModel() {

    private val _getPlantState = MutableStateFlow<Resource<PlantAdmin>>(Resource.Loading())
    val getPlantState: StateFlow<Resource<PlantAdmin>> = _getPlantState.asStateFlow()

    private val _postPlantState = MutableStateFlow<Resource<Boolean>?>(null)
    val postPlantState: StateFlow<Resource<Boolean>?> = _postPlantState.asStateFlow()

    fun getPlantDetail(id: String) = viewModelScope.launch {
        val res = repo.getModel(
            Constants.FirebaseColl.PLANTS,
            id
        )?.toObject(PlantAdmin::class)
        res?.let {
            _getPlantState.emit(Resource.Success(res))
        } ?: run {
            _getPlantState.emit(Resource.Error("Error"))
        }
    }

    fun updatePlantDetail(
        id: String,
        plant: PlantAdmin,
        image: ByteArray?
    ) = viewModelScope.launch {
        _postPlantState.emit(Resource.Loading())
        image?.let {
            val resUrl = repo.uploadFileAndGetUrl(
                Constants.FirebaseColl.PLANTS,
                id,
                image
            )
            if(resUrl.isSuccessful) {
                val updatedPlant = plant.copy(
                    imageUrl = resUrl.data as String
                )
                savePlant(
                    id,
                    updatedPlant
                )
            } else {
                _postPlantState.emit(Resource.Error(resUrl.message))
            }
        } ?: run {
            savePlant(
                id = id,
                plant = plant
            )
        }
    }

    fun savePlantDetail(image: ByteArray?, plant: PlantAdmin) = viewModelScope.launch {
        _postPlantState.emit(Resource.Loading())
        val collection = "${Constants.FirebaseColl.UTIL}/"
        val doc = "${Constants.FirebaseDoc.LAST_PLANT_ID}/"
        val field = Constants.FirebaseField.ID
        val address = collection + doc + field
        val newId = repo.generateNewId(address)
        newId?.let {
            val newPlant = plant.copy(
                id = newId.toString()
            )
            updatePlantDetail(
                id = newId.toString(),
                plant = newPlant,
                image = image
            )
        } ?: run {
            _postPlantState.emit(Resource.Error("Error while generating id"))
        }
    }

    private suspend fun savePlant(
        id: String,
        plant: PlantAdmin
    ) {
        val resPlant = repo.saveModel(
            Constants.FirebaseColl.PLANTS,
            id,
            plant
        )
        if(resPlant.isSuccessful) {
            _postPlantState.emit(Resource.Success(true))
        } else {
            _postPlantState.emit(Resource.Error(resPlant.message))
        }
    }

}