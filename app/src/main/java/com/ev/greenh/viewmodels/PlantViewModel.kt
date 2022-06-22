package com.ev.greenh.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.models.*
import com.ev.greenh.models.uimodels.MyOrder
import com.ev.greenh.models.uimodels.MyOrderDetail
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

    private val _bagItems:MutableLiveData<ViewModelEventWrapper<Resource<Map<Plant, String>>>> = MutableLiveData()
    val bagItems : LiveData<ViewModelEventWrapper<Resource<Map<Plant, String>>>>
        get() = _bagItems

    private val _uid:MutableLiveData<ViewModelEventWrapper<Resource<String?>>> = MutableLiveData()
    val uid: LiveData<ViewModelEventWrapper<Resource<String?>>>
        get() = _uid

    private val _profile:MutableLiveData<Resource<Profile>> = MutableLiveData()
    val profile:LiveData<Resource<Profile>>
        get() =  _profile

    private val _singleTimeProfile:MutableLiveData<ViewModelEventWrapper<Resource<Profile>>> = MutableLiveData()
    val singleTimeProfile:LiveData<ViewModelEventWrapper<Resource<Profile>>>
        get() = _singleTimeProfile

    // add wrapper
    private val _success:MutableLiveData<Resource<Response>> = MutableLiveData()
    val success:LiveData<Resource<Response>>
        get() = _success

    // Add wrapper
    private val _deleteBagItem:MutableLiveData<Resource<Response>> = MutableLiveData()
    val deleteBagItem :LiveData<Resource<Response>>
        get() = _deleteBagItem

    private val _getUserOrders:MutableLiveData<Resource<List<MyOrder>>> = MutableLiveData()
    val getUserOrders:LiveData<Resource<List<MyOrder>>>
        get() = _getUserOrders

    private val _getSingleOrderDetails: MutableLiveData<Resource<MyOrderDetail>> = MutableLiveData()
    val getSingleOrderDetails:LiveData<Resource<MyOrderDetail>>
        get() = _getSingleOrderDetails

    private val _razorpayOrderId:MutableLiveData<ViewModelEventWrapper<Resource<RazorpayOrderId>>> = MutableLiveData()
    val razorpayOrderId:LiveData<ViewModelEventWrapper<Resource<RazorpayOrderId>>>
        get() = _razorpayOrderId

    private val _placeOrder:MutableLiveData<ViewModelEventWrapper<Resource<Response>>> = MutableLiveData()
    val placeOrder:LiveData<ViewModelEventWrapper<Resource<Response>>>
        get() = _placeOrder

    private val _emptyUserCart:MutableLiveData<ViewModelEventWrapper<Resource<Response>>> = MutableLiveData()
    val emptyUserCart:LiveData<ViewModelEventWrapper<Resource<Response>>>
        get() = _emptyUserCart

    private val _bagItemIds:MutableLiveData<Resource<List<String>>> = MutableLiveData()
    val bagItemIds : LiveData<Resource<List<String>>>
        get() = _bagItemIds

    private val _updateProfile:MutableLiveData<ViewModelEventWrapper<Resource<Response>>> = MutableLiveData()
    val updateProfile: LiveData<ViewModelEventWrapper<Resource<Response>>>
        get() = _updateProfile

    fun getAllPlants(collection: String) = viewModelScope.launch {
        _plantsResponse.value = repository.getAllPlants(collection)
    }

    fun getSinglePlant(collection: String, id:String) = viewModelScope.launch {
        _plantResponse.value = repository.getSinglePlant(collection,id)
    }

    fun readUid() = viewModelScope.launch {
        _uid.value = ViewModelEventWrapper(repository.readUid())
    }

    fun addPlantToBag(plantId:String,user:String,collection:String,quantity:String) = viewModelScope.launch {
        _success.value = repository.addPlantToBag(plantId, user, collection, quantity)
    }

    fun getBagItems(collBag: String,collPlant:String,user:String) = viewModelScope.launch {
        _bagItems.value = ViewModelEventWrapper(repository.getBagItems(collBag, collPlant, user))
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

    fun placeOrder(order: Order, collection: String) = viewModelScope.launch {
        _placeOrder.value = ViewModelEventWrapper(repository.placeOrder(order, collection))
    }

    fun getUserOrders(user:String,collectionOrder: String, collectionPlant:String) = viewModelScope.launch {
        _getUserOrders.value = repository.getUserOrders(user, collectionOrder, collectionPlant)
    }

    fun getSingleOrderDetail(orderId:String,collectionOrder: String, collectionPlant: String) = viewModelScope.launch {
        _getSingleOrderDetails.value = repository.getSingleOrderDetail(orderId, collectionOrder, collectionPlant)
    }

    fun generateOrderId(amount:HashMap<String,Int>) =viewModelScope.launch {
        _razorpayOrderId.value = ViewModelEventWrapper(repository.generateOrderId(amount))
    }

    fun emptyUserCart(user:String,collection: String) = viewModelScope.launch {
        _emptyUserCart.value = ViewModelEventWrapper(repository.emptyUserCart(user, collection))
    }

    fun getBagItemIds(email:String,collection: String) = viewModelScope.launch {
        _bagItemIds.value = repository.getBagItemIds(email, collection)
    }

    fun updateUserDetails(collection: String,profile:Profile) = viewModelScope.launch {
        _updateProfile.value = ViewModelEventWrapper(repository.updateUserDetails(collection, profile))
    }

    fun getProfileSingleTime(collection: String,uid:String) = viewModelScope.launch {
        _singleTimeProfile.value = ViewModelEventWrapper(repository.getUserDetails(collection,uid))
    }

}