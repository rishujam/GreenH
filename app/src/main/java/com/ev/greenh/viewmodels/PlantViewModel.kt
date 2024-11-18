package com.ev.greenh.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.core.util.Resource
import com.ev.greenh.models.*
import com.ev.greenh.models.uimodels.MyOrder
import com.ev.greenh.models.uimodels.MyOrderDetail
import com.ev.greenh.shop.data.repo.PlantRepository
import com.ev.greenh.shop.data.model.ResPlant
import com.ev.greenh.util.ViewModelEventWrapper
import com.example.auth.data.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(
    private val repository: PlantRepository
) :ViewModel(){

    //todo get context and check internet connection before each network call

    var currentFilter = "All"

    // For list of all plants
    var plantsPage = 0
    var lastFeatureNoAll = 0
    var plantsResponse:ResPlants?=null
    val plants:MutableLiveData<Resource<ResPlants>> = MutableLiveData()

    //For list of indoor Plants
    var plantsIndoorPage = 0
    var lastFeatureNoIndoor = 0
    var plantsIndoorResponse : ResPlants?=null
    val plantsIndoor:MutableLiveData<Resource<ResPlants>> = MutableLiveData()

    //For list of table Plants
    var plantsTablePage = 0
    var lastFeatureNoTable = 0
    var plantsTableResponse:ResPlants?=null
    val plantsTable: MutableLiveData<Resource<ResPlants>> = MutableLiveData()

    //For single plant
    private val _plantResponse: MutableLiveData<Resource<ResPlant>> = MutableLiveData()
    val plantResponse: LiveData<Resource<ResPlant>>
        get() = _plantResponse

    private val _bagItems:MutableLiveData<ViewModelEventWrapper<Resource<Map<ResPlant, String>>>> = MutableLiveData()
    val bagItems : LiveData<ViewModelEventWrapper<Resource<Map<ResPlant, String>>>>
        get() = _bagItems

    private val _uid:MutableLiveData<Resource<String?>> = MutableLiveData()
    val uid: LiveData<Resource<String?>>
        get() = _uid

    private val _profile:MutableLiveData<Resource<UserProfile>> = MutableLiveData()
    val profile:LiveData<Resource<UserProfile>>
        get() =  _profile

    private val _singleTimeProfile:MutableLiveData<ViewModelEventWrapper<Resource<UserProfile>>> = MutableLiveData()
    val singleTimeProfile:LiveData<ViewModelEventWrapper<Resource<UserProfile>>>
        get() = _singleTimeProfile

    // add wrapper
    private val _success:MutableLiveData<ViewModelEventWrapper<Resource<Response>>> = MutableLiveData()
    val success:LiveData<ViewModelEventWrapper<Resource<Response>>>
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

    private val _razorpayOrderId:MutableLiveData<ViewModelEventWrapper<Resource<RazorpayOrderId?>>> = MutableLiveData()
    val razorpayOrderId:LiveData<ViewModelEventWrapper<Resource<RazorpayOrderId?>>>
        get() = _razorpayOrderId

    private val _placeOrder:MutableLiveData<ViewModelEventWrapper<Resource<Response>>> = MutableLiveData()
    val placeOrder:LiveData<ViewModelEventWrapper<Resource<Response>>>
        get() = _placeOrder

    private val _bagItemIds:MutableLiveData<Resource<List<String>>> = MutableLiveData()
    val bagItemIds : LiveData<Resource<List<String>>>
        get() = _bagItemIds

    private val _updateProfile:MutableLiveData<ViewModelEventWrapper<Resource<Response>>> = MutableLiveData()
    val updateProfile: LiveData<ViewModelEventWrapper<Resource<Response>>>
        get() = _updateProfile

    private val  _cancelOrderReq:MutableLiveData<ViewModelEventWrapper<Resource<Response>>> = MutableLiveData()
    val cancelOrderReq: LiveData<ViewModelEventWrapper<Resource<Response>>>
        get() = _cancelOrderReq

    private val _videoUrl:MutableLiveData<ViewModelEventWrapper<Resource<String>>> = MutableLiveData()
    val videoUrl:LiveData<ViewModelEventWrapper<Resource<String>>>
        get() = _videoUrl

    private val _allPlantsPaginated:MutableLiveData<PagingData<ResPlant>> = MutableLiveData()
    val allPlantsPaginated:LiveData<PagingData<ResPlant>>
        get() = _allPlantsPaginated

    val apiKey:MutableLiveData<ViewModelEventWrapper<Resource<String>>> = MutableLiveData()

    val minVersion:MutableLiveData<Resource<Int>> = MutableLiveData()

    fun getAllPlantsPaginated(){
//        _allPlantsPaginated.value = repository.getAllPlantsPaging().cachedIn(viewModelScope).value
    }

    fun getAllPlants(collection: String) = viewModelScope.launch(Dispatchers.IO) {
        plants.postValue(Resource.Loading())
//        val response = repository.getAllPlants(collection,plantsPage, lastFeatureNoAll)
//        plants.postValue(handlePlantsResponse(response))
    }

    private fun handlePlantsResponse(response: Resource<ResPlants>) : Resource<ResPlants> {
        response.data?.let {//TODO added temporarily needed to be handled
            try {
                plantsPage++
                if(plantsResponse==null){
                    plantsResponse = it
//                    lastFeatureNoAll = it.plants[it.plants.lastIndex].featureNo
                }else{
                    val oldPlants = plantsResponse?.plants
                    val newPlants = it.plants
//                    oldPlants?.addAll(newPlants)
//                    lastFeatureNoAll = oldPlants!![oldPlants.lastIndex].featureNo
                }
                return Resource.Success(plantsResponse ?:it)
            } catch (e: Exception) {
                return Resource.Error("")
            }
        }
        return Resource.Error("")
    }

    fun getIndoorPlants(collection: String) = viewModelScope.launch(Dispatchers.IO) {
        plantsIndoor.postValue(Resource.Loading())
//        val response = repository.getPlantsByCategory(collection,"Indoor", lastFeatureNoIndoor)
//        plantsIndoor.postValue(handlePlantsIndoorResponse(response))
    }

    private fun handlePlantsIndoorResponse(response:Resource<ResPlants>):Resource<ResPlants>{
        response.data?.let {
            plantsIndoorPage++
            if(plantsIndoorResponse==null){
                plantsIndoorResponse = it
//                lastFeatureNoIndoor = it.plants[it.plants.lastIndex].featureNo
            }else{
                val oldPlantsByCategory = plantsIndoorResponse?.plants
                val newPlantsByCategory = it.plants
//                oldPlantsByCategory?.addAll(newPlantsByCategory)
//                lastFeatureNoIndoor = oldPlantsByCategory!![oldPlantsByCategory.lastIndex].featureNo
            }
            return Resource.Success(plantsIndoorResponse ?:it)
        }
        return Resource.Error(response.message)
    }

    fun getTablePlants(collection: String) = viewModelScope.launch(Dispatchers.IO) {
        plantsTable.postValue(Resource.Loading())
//        val response = repository.getPlantsByCategory(collection,"Table", lastFeatureNoTable)
//        plantsTable.postValue(handlePlantsTableResponse(response))
    }

    private fun handlePlantsTableResponse(response: Resource<ResPlants>) : Resource<ResPlants> {
        response.data?.let {
            try { //TODO added it just to hide crash at table plants need to work on this
                plantsTablePage++
                if(plantsTableResponse==null){
                    plantsTableResponse = it
//                    lastFeatureNoTable = it.plants[it.plants.lastIndex].featureNo
                }else{
                    val oldPlantsByCategory = plantsTableResponse?.plants
                    val newPlantsByCategory = it.plants
//                    oldPlantsByCategory?.addAll(newPlantsByCategory)
//                    lastFeatureNoTable = oldPlantsByCategory!![oldPlantsByCategory.lastIndex].featureNo
                }
                return Resource.Success(plantsTableResponse ?:it)
            } catch (_:Exception) { }
        }
        return Resource.Error(response.message)
    }

    fun getSinglePlant(collection: String, id:String) = viewModelScope.launch {
//        _plantResponse.value = repository.getSinglePlant(collection,id)
    }

//    fun readUid() = viewModelScope.launch {
//        _uid.value = userDataRepository.readUid()?.let {
//            Resource.Success(it)
//        } ?: run {
//            Resource.Error("Null")
//        }
//    }

    fun addPlantToBag(plantId:String,user:String,collection:String,quantity:String) = viewModelScope.launch {
//        _success.value = ViewModelEventWrapper(repository.addPlantToBag(plantId, user, collection, quantity))
    }

    fun getBagItems(collBag: String,collPlant:String,user:String) = viewModelScope.launch {
//        _bagItems.value = ViewModelEventWrapper(repository.getBagItems(collBag, collPlant, user))
    }

    fun updateQuantity(user:String,collection: String,newQuantity:Int,plantId: String) = viewModelScope.launch {
//        _success.value = ViewModelEventWrapper(repository.updateQuantity(user, collection, newQuantity, plantId))
    }

    fun deleteItemFromBag(user:String,collection: String,plantId: String) = viewModelScope.launch {
//        _deleteBagItem.value = repository.deleteItemFromBag(user, collection, plantId)
    }

    fun getUserDetails(collection: String,email:String) = viewModelScope.launch {
//        _profile.value = repository.getUserDetails(collection, email)
    }

    fun placeOrder(order: Order, collection: String) = viewModelScope.launch(Dispatchers.IO) {
//        _placeOrder.value = ViewModelEventWrapper(repository.placeOrder(order, collection))
    }

    fun getUserOrders(user:String,collectionOrder: String, collectionPlant:String) = viewModelScope.launch(Dispatchers.IO) {
//        _getUserOrders.value = repository.getUserOrders(user, collectionOrder, collectionPlant)
    }

    fun getSingleOrderDetail(orderId:String,collectionOrder: String, collectionPlant: String) = viewModelScope.launch(Dispatchers.IO) {
//        _getSingleOrderDetails.value = repository.getSingleOrderDetail(orderId, collectionOrder, collectionPlant)
    }

    fun generateOrderId(amount:HashMap<String,Int>) =viewModelScope.launch(Dispatchers.IO) {
//        _razorpayOrderId.value = ViewModelEventWrapper(repository.generateOrderId(amount))
    }

    fun emptyUserCart(user:String,collection: String) = viewModelScope.launch(Dispatchers.IO) {
//        ViewModelEventWrapper(repository.emptyUserCart(user, collection))
    }

    fun getBagItemIds(email:String,collection: String) = viewModelScope.launch(Dispatchers.IO) {
//        _bagItemIds.value = repository.getBagItemIds(email, collection)
    }

    fun updateUserDetails(collection: String,profile:UserProfile) = viewModelScope.launch(Dispatchers.IO) {
//        val userDetails = repository.updateUserDetails(collection, profile)
//        withContext(Dispatchers.Main) {
//            _updateProfile.value = ViewModelEventWrapper(userDetails)
//        }
    }

    fun getProfileSingleTime(collection: String,uid:String) = viewModelScope.launch(Dispatchers.IO) {
//        _singleTimeProfile.value = ViewModelEventWrapper(repository.getUserDetails(collection,uid))
    }

    fun sendCancelRequest(orderId:String,collection: String) = viewModelScope.launch(Dispatchers.IO) {
//        _cancelOrderReq.value = ViewModelEventWrapper(repository.sendCancelRequest(orderId,collection))
    }

    fun getApiKey(collection: String) = viewModelScope.launch(Dispatchers.IO) {
//        val apiKey = repository.getApiKey(collection)
        withContext(Dispatchers.Main) {
//            this@PlantViewModel.apiKey.value = ViewModelEventWrapper(apiKey)
        }
    }

    fun getMinVersionToRun(collection: String) = viewModelScope.launch(Dispatchers.IO) {
//        val minVersion = repository.getMinVersionToRun(collection)
        withContext(Dispatchers.Main) {
//            this@PlantViewModel.minVersion.value = minVersion
        }
    }

    fun getPlantVideoUrl(collection:String, plantId: String) = viewModelScope.launch(Dispatchers.IO) {
//        val url = repository.getPlantVideoUrl(collection, plantId)
//        withContext(Dispatchers.Main) {
//            _videoUrl.value = ViewModelEventWrapper(url)
//        }

    }
}