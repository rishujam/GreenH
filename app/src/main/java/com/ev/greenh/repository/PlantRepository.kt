package com.ev.greenh.repository

import android.util.Log
import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.localdatastore.UserPreferences
import com.ev.greenh.models.Order
import com.ev.greenh.models.Profile
import com.ev.greenh.razorpayapi.RazorpayInstance
import com.ev.greenh.util.Resource


class PlantRepository(
    private val source: FirestoreSource,
    private val preferences: UserPreferences
) : BaseRepository(){

    suspend fun readEmail() =safeApiCall {
        preferences.readData()
    }

    suspend fun getAllPlants(collection:String) = safeApiCall{
        source.getSamplePlants(collection)
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

    suspend fun updateAddress(collection: String,email:String, address:String,name:String) =safeApiCall {
        source.updateAddress(collection, email, address, name)
    }

    suspend fun generateOrderId(amount:HashMap<String,Int>)= safeApiCall {
        RazorpayInstance.api.generateOrderId(amount)
    }

    suspend fun placeOrder(order: Order, collection: String)=safeApiCall {
        source.placeOrder(order, collection)
    }

    suspend fun getUserOrders(user:String,collectionOrder: String, collectionPlant:String)=safeApiCall {
        source.getUserOrders(user, collectionOrder, collectionPlant)
    }

    suspend fun getSingleOrder(orderId:String,collection: String) = safeApiCall {
        source.getSingleOrder(orderId, collection)
    }

    suspend fun emptyUserCart(user:String,collection: String)= safeApiCall {
        source.emptyUserCart(user, collection)
    }

}