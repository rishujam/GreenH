package com.ev.greenh.repository

import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.localdatastore.UserPreferences
import com.ev.greenh.models.Profile


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

    suspend fun saveProfile(collection: String,profile: Profile) = safeApiCall {
        source.saveProfile(collection, profile)
    }

    suspend fun getUserDetails(collection: String,email:String) = safeApiCall {
        source.getUserDetails(collection, email)
    }
}