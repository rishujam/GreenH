package com.ev.greenh.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ev.greenh.common.commondata.ApiIdentifier
import com.ev.greenh.common.commondata.RetrofitPool
import com.ev.greenh.common.commondata.api.RazorpayApi
import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.localdatastore.UserPreferences
import com.ev.greenh.models.Order
import com.ev.greenh.models.Profile
import com.ev.greenh.pagging.PlantsPagingSource
import com.ev.greenh.util.Constants.QUERY_PAGE_SIZE

class PlantRepository(
    private val source: FirestoreSource,
    private val preferences: UserPreferences
) : BaseRepository(){


    suspend fun readUid() = safeApiCall {
        preferences.readUid()
    }

    suspend fun getAllPlants(collection:String, page:Int, lastFeatureNo: Int) = safeApiCall{
        source.getAllPlants(collection,page, lastFeatureNo)
    }

    fun getAllPlantsPaging() = Pager(
        config = PagingConfig(pageSize = QUERY_PAGE_SIZE, maxSize = 100),
        pagingSourceFactory = { PlantsPagingSource(source) }
    ).liveData

    suspend fun getPlantsByCategory(collection: String, category:String, lastFeatureNo:Int) = safeApiCall {
        source.getPlantsByCategory(collection, category, lastFeatureNo)
    }

    suspend fun getSinglePlant(collection: String,plantId:String) = safeApiCall {
        source.getSinglePlant(collection,plantId)
    }

    suspend fun addPlantToBag(plantId:String,user:String,collection:String,quantity:String) = safeApiCall {
        source.addPlantToBag(plantId, user, collection, quantity)
    }

    suspend fun getBagItems(collBag: String,collPlant:String,user:String)= safeApiCall {
        source.getBagItems(collBag, collPlant, user)
    }

    suspend fun updateQuantity(user:String,collection: String,newQuantity:Int,plantId: String) = safeApiCall {
        source.updateQuantity(user, collection, newQuantity, plantId)
    }

    suspend fun deleteItemFromBag(user:String,collection: String,plantId: String) = safeApiCall {
        source.deleteItemFromBag(user, collection, plantId)
    }

    suspend fun getUserDetails(collection: String,email:String)= safeApiCall {
        source.getUserDetails(collection, email)
    }

    suspend fun generateOrderId(amount:HashMap<String,Int>)= safeApiCall {
        val api = RetrofitPool.getApi(ApiIdentifier.Razorpay).service as? RazorpayApi
        api?.generateOrderId(amount)
    }

    suspend fun placeOrder(order: Order, collection: String)=safeApiCall {
        source.placeOrder(order, collection)
    }

    suspend fun getUserOrders(user:String,collectionOrder: String, collectionPlant:String)=safeApiCall {
        source.getUserOrders(user, collectionOrder, collectionPlant)
    }

    suspend fun getSingleOrderDetail(orderId:String,collectionOrder: String, collectionPlant: String) = safeApiCall {
        source.getSingleOrderDetails(orderId, collectionOrder, collectionPlant)
    }

    suspend fun emptyUserCart(user:String,collection: String)= safeApiCall {
        source.emptyUserCart(user, collection)
    }

    suspend fun getBagItemIds(email:String,collection: String) = safeApiCall {
        source.getBagItemIds(email, collection)
    }

    suspend fun updateUserDetails(collection: String,profile:Profile) = safeApiCall {
        source.updateUserDetails(collection, profile)
    }

    suspend fun sendCancelRequest(orderId:String,collection: String)=safeApiCall {
        source.sendCancelRequest(orderId,collection)
    }

    suspend fun getApiKey(collection: String) = safeApiCall {
        source.getApiKey(collection)
    }

    suspend fun getMinVersionToRun(collection: String) = safeApiCall {
        source.getMinVersionToRun(collection)
    }

    suspend fun getPlantVideoUrl(collection: String, plantId:String)= safeApiCall {
        source.getPlantVideoUrl(collection, plantId)
    }
}