package com.ev.greenh.repository

import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.localdatastore.UserPreferences


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
}