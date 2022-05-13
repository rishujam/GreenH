package com.ev.greenh.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.models.*
import com.ev.greenh.models.uimodels.MyOrder
import com.ev.greenh.repository.PlantRepository
import com.ev.greenh.util.Resource
import com.ev.greenh.util.ViewModelEventWrapper
import kotlinx.coroutines.launch

class PlantViewModel(
    private val repository: PlantRepository
) :ViewModel(){

    //todo get context and check internet connection before each network call

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

    private val _profile:MutableLiveData<Resource<Profile>> = MutableLiveData()
    val profile:LiveData<Resource<Profile>>
        get() =  _profile

    // add wrapper
    private val _success:MutableLiveData<Resource<Response>> = MutableLiveData()
    val success:LiveData<Resource<Response>>
        get() = _success

    // Add wrapper
    private val _deleteBagItem:MutableLiveData<Resource<Response>> = MutableLiveData()
    val deleteBagItem :LiveData<Resource<Response>>
        get() = _deleteBagItem

    // Add wrapper
    private val _updateAddress:MutableLiveData<Resource<Response>> = MutableLiveData()
    val updateAddress:LiveData<Resource<Response>>
        get() = _updateAddress

    private val _getUserOrders:MutableLiveData<Resource<List<MyOrder>>> = MutableLiveData()
    val getUserOrders:LiveData<Resource<List<MyOrder>>>
        get() = _getUserOrders

    private val _getSingleOrder: MutableLiveData<Resource<Order>> = MutableLiveData()
    val getSingleOrder:LiveData<Resource<Order>>
        get() = _getSingleOrder

    private val _razorpayOrderId:MutableLiveData<ViewModelEventWrapper<Resource<RazorpayOrderId>>> = MutableLiveData()
    val razorpayOrderId:LiveData<ViewModelEventWrapper<Resource<RazorpayOrderId>>>
        get() = _razorpayOrderId

    private val _placeOrder:MutableLiveData<ViewModelEventWrapper<Resource<Response>>> = MutableLiveData()
    val placeOrder:LiveData<ViewModelEventWrapper<Resource<Response>>>
        get() = _placeOrder

    private val _emptyUserCart:MutableLiveData<ViewModelEventWrapper<Resource<Response>>> = MutableLiveData()
    val emptyUserCart:LiveData<ViewModelEventWrapper<Resource<Response>>>
        get() = _emptyUserCart

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

    fun getUserDetails(collection: String,email:String) = viewModelScope.launch {
        _profile.value = repository.getUserDetails(collection, email)
    }

    fun updateAddress(collection: String,email:String, address:String,name:String) = viewModelScope.launch {
        _updateAddress.value = repository.updateAddress(collection, email, address, name)
    }

    fun placeOrder(order: Order, collection: String) = viewModelScope.launch {
        _placeOrder.value = ViewModelEventWrapper(repository.placeOrder(order, collection))
    }

    fun getUserOrders(user:String,collectionOrder: String, collectionPlant:String) = viewModelScope.launch {
        _getUserOrders.value = repository.getUserOrders(user, collectionOrder, collectionPlant)
    }

    fun getSingleOrder(orderId:String,collection: String) = viewModelScope.launch {
        _getSingleOrder.value = repository.getSingleOrder(orderId, collection)
    }

    fun generateOrderId(amount:HashMap<String,Int>) =viewModelScope.launch {
        _razorpayOrderId.value = ViewModelEventWrapper(repository.generateOrderId(amount))
    }

    fun emptyUserCart(user:String,collection: String) = viewModelScope.launch {
        _emptyUserCart.value = ViewModelEventWrapper(repository.emptyUserCart(user, collection))
    }

}