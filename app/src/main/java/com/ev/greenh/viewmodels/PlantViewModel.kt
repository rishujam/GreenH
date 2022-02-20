package com.ev.greenh.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.models.Plant
import com.ev.greenh.models.Response
import com.ev.greenh.repository.PlantRepository
import com.ev.greenh.util.Resource
import kotlinx.coroutines.launch

class PlantViewModel(
    private val repository: PlantRepository
) :ViewModel(){

    private val _plantsResponse : MutableLiveData<Resource<List<Plant>>> = MutableLiveData()
    val plantsResponse: LiveData<Resource<List<Plant>>>
        get() = _plantsResponse

    private val _plantResponse: MutableLiveData<Resource<Plant>> = MutableLiveData()
    val plantResponse: LiveData<Resource<Plant>>
        get() = _plantResponse

    private val _bagItems:MutableLiveData<Resource<Map<Plant, String>>> = MutableLiveData()
    val bagItems : LiveData<Resource<Map<Plant, String>>>
        get() = _bagItems

    private val _email:MutableLiveData<Resource<String?>> = MutableLiveData()
    val email: LiveData<Resource<String?>>
        get() = _email

    private val _success:MutableLiveData<Resource<Response>> = MutableLiveData()
    val success:LiveData<Resource<Response>>
        get() = _success

    private val _deleteBagItem:MutableLiveData<Resource<Response>> = MutableLiveData()
    val deleteBagItem :LiveData<Resource<Response>>
        get() = _deleteBagItem


    fun getAllPlants(collection: String) = viewModelScope.launch {
        _plantsResponse.value = repository.getAllPlants(collection)
    }

    fun getSinglePlant(collection: String, id:String) = viewModelScope.launch {
        _plantResponse.value = repository.getSinglePlant(collection,id)
    }

    fun readEmail() = viewModelScope.launch {
        _email.value = repository.readEmail()
    }

    fun addPlantToBag(plantId:String,user:String,collection:String,quantity:String) = viewModelScope.launch {
        _success.value = repository.addPlantToBag(plantId, user, collection, quantity)
    }

    fun getBagItems(collBag: String,collPlant:String,user:String) = viewModelScope.launch {
        _bagItems.value = repository.getBagItems(collBag, collPlant, user)
    }

    fun updateQuantity(user:String,collection: String,newQuantity:Int,plantId: String) = viewModelScope.launch {
        _success.value = repository.updateQuantity(user, collection, newQuantity, plantId)
    }

    fun deleteItemFromBag(user:String,collection: String,plantId: String) = viewModelScope.launch {
        _deleteBagItem.value = repository.deleteItemFromBag(user, collection, plantId)
    }

}