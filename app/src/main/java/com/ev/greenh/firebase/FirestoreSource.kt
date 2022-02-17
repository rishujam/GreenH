package com.ev.greenh.firebase

import android.util.Log
import com.ev.greenh.models.Plant
import com.ev.greenh.models.Response
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class FirestoreSource {

    private val storageRef = Firebase.storage.reference
    private val fireRef = Firebase.firestore

    suspend fun getSamplePlants(collection:String):List<Plant>{
        val list = mutableListOf<Plant>()
        val data = fireRef.collection(collection).get().await()
        for(i in data.documents){
            val plant = i.toObject<Plant>()
            if(plant!=null){
                list.add(plant)
            }
        }
        return list
    }
    suspend fun getSinglePlant(collection: String,id:String): Plant {
        val ref = fireRef.collection(collection).document(id).get().await()
        val plant = ref.toObject<Plant>()
        return plant!!
    }

    suspend fun addPlantToBag(plantId:String,user:String,collection:String,quantity:String): Response {
        //checking if same plant exist
        var q = quantity.toInt()
        val ref = fireRef.collection(collection).document(user).get().await()
        val data = ref.data
        if(data!=null){
            if(data.keys.contains(plantId)){
                q+=ref[plantId].toString().toInt()
            }
        }
        //adding quantity
        fireRef.collection(collection).document(user).set(mapOf(plantId to q.toString()), SetOptions.merge()).await()
        return Response(true)
    }

    suspend fun getBagItems(collBag: String,collPlant:String,user:String):Map<Plant,String>{
        val map = mutableMapOf<Plant,String>()
        val ref = fireRef.collection(collBag).document(user).get().await()
        val data = ref.data
        if(data!=null){
            coroutineScope {
                for(i in data.keys){
                    async {
                        val plant = getSinglePlant(collPlant,i)
                        val quantity = ref[plant.id].toString()
                        val price = plant.price.toInt()*quantity.toInt()
                        map[plant] = "$quantity,$price"
                    }
                }
            }
        }
        return map
    }
}