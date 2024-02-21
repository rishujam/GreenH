package com.ev.greenh.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import com.ev.greenh.models.Order
import com.ev.greenh.models.Plant
import com.ev.greenh.models.Profile
import com.ev.greenh.models.RazorpayOrderId
import com.ev.greenh.models.Response
import com.ev.greenh.repository.PlantRepository
import com.ev.greenh.util.ViewModelEventWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(
    val repository: PlantRepository
) : ViewModel() {

    val apiKey: MutableLiveData<ViewModelEventWrapper<Resource<String>>> = MutableLiveData()

    private val _razorpayOrderId: MutableLiveData<ViewModelEventWrapper<Resource<RazorpayOrderId?>>> =
        MutableLiveData()
    val razorpayOrderId: LiveData<ViewModelEventWrapper<Resource<RazorpayOrderId?>>>
        get() = _razorpayOrderId

    private val _placeOrder: MutableLiveData<ViewModelEventWrapper<Resource<Response>>> =
        MutableLiveData()
    val placeOrder: LiveData<ViewModelEventWrapper<Resource<Response>>>
        get() = _placeOrder

    private val _singleTimeProfile: MutableLiveData<ViewModelEventWrapper<Resource<Profile>>> =
        MutableLiveData()
    val singleTimeProfile: LiveData<ViewModelEventWrapper<Resource<Profile>>>
        get() = _singleTimeProfile

    private val _bagItems: MutableLiveData<ViewModelEventWrapper<Resource<Map<Plant, String>>>> =
        MutableLiveData()
    val bagItems: LiveData<ViewModelEventWrapper<Resource<Map<Plant, String>>>>
        get() = _bagItems

    private val _uid: MutableLiveData<ViewModelEventWrapper<Resource<String?>>> = MutableLiveData()
    val uid: LiveData<ViewModelEventWrapper<Resource<String?>>>
        get() = _uid

    fun getApiKey(collection: String) = viewModelScope.launch(Dispatchers.IO) {
        apiKey.value = ViewModelEventWrapper(repository.getApiKey(collection))
    }

    fun getProfileSingleTime(collection: String, uid: String) = viewModelScope.launch(Dispatchers.IO) {
        _singleTimeProfile.value = ViewModelEventWrapper(repository.getUserDetails(collection, uid))
    }

    fun generateOrderId(amount:HashMap<String,Int>) =viewModelScope.launch {
        _razorpayOrderId.value = ViewModelEventWrapper(repository.generateOrderId(amount))
    }

    fun emptyUserCart(user: String, collection: String) = viewModelScope.launch {
        ViewModelEventWrapper(repository.emptyUserCart(user, collection))
    }

    fun placeOrder(order: Order, collection: String) = viewModelScope.launch {
        _placeOrder.value = ViewModelEventWrapper(repository.placeOrder(order, collection))
    }

    fun getBagItems(collBag: String, collPlant: String, user: String) = viewModelScope.launch {
        _bagItems.value = ViewModelEventWrapper(repository.getBagItems(collBag, collPlant, user))
    }
}