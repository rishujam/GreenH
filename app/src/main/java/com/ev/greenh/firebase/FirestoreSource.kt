package com.ev.greenh.firebase

import android.util.Log
import com.ev.greenh.models.Order
import com.ev.greenh.models.Plant
import com.ev.greenh.models.Profile
import com.ev.greenh.models.Response
import com.ev.greenh.models.uimodels.MyOrder
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class FirestoreSource {

    //private val storageRef = Firebase.storage.reference
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

    suspend fun updateQuantity(user:String,collection: String,newQuantity:Int,plantId: String):Response{
        val res = Response()
        val ref = fireRef.collection(collection).document(user)
        Firebase.firestore.runTransaction { transaction ->
            transaction.update(ref,plantId,newQuantity.toString())
            null
        }.addOnSuccessListener {
            res.success = true
        }.addOnFailureListener {
            res.errorMsg = it.message
        }.await()
        return res
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

    suspend fun deleteItemFromBag(user:String,collection: String,plantId: String):Response{
        val res = Response()
        val docRef = fireRef.collection(collection).document(user)
        val updates = hashMapOf<String,Any>(
            plantId to FieldValue.delete()
        )
        docRef.update(updates).addOnSuccessListener {
            res.success = true
        }.addOnFailureListener {
            res.errorMsg = it.message
        }.await()
        return res
    }

    suspend fun getUserDetails(collection: String, email: String): Profile {
        val snap = fireRef.collection(collection).document(email).get().await()
        return snap.toObject<Profile>()!!
    }

    suspend fun updateAddress(collection: String,email:String, address:String,name:String):Response {
        //Complete Profile Function and update the completeProfile Boolean.
        val response = Response()
        return try {
            fireRef.collection(collection).document(email).update(mapOf("address" to address, "name" to name, "profileComplete" to true)).await()
            response.success =true
            response
        }catch (e:Exception){
            response.errorMsg = e.message
            response
        }
    }

    suspend fun placeOrder(order:Order,collection: String):Response{
        val response = Response()
        return try {
            fireRef.collection(collection).document(order.orderId).set(order).await()
            response.success = true
            response
        }catch (e:Exception){
            response.errorMsg=  e.message
            response
        }
    }

    suspend fun getUserOrders(user:String,collectionOrder: String, collectionPlant:String):List<MyOrder>{
        val out = mutableListOf<MyOrder>()
        val snap = fireRef.collection(collectionOrder).get().await()
        for(i in snap.documents){
            val order = i.toObject<Order>()
            if(order!=null && order.user == user){
                val myOrder = MyOrder(orderId = order.orderId, deliveryStatus = order.deliveryStatus, deliveryDate = order.dateDelivered, orderedDate = order.dateOrdered)
                for(plantId in order.items){
                    val plant = getSinglePlant(collectionPlant,plantId.split(",")[0])
                    myOrder.plantName = plant.name
                    myOrder.plantPhoto = plant.imageLocation
                    out.add(myOrder)
                }
            }
        }
        return out
    }

    suspend fun getSingleOrder(orderId:String,collection: String):Order{
        val snap = fireRef.collection(collection).document(orderId).get().await()
        return snap.toObject<Order>()!!
    }

    suspend fun emptyUserCart(user:String,collection: String):Response{
        val response = Response()
        return try {
            fireRef.collection(collection).document(user).delete().await()
            response.success = true
            response
        }catch (e:Exception){
            response.errorMsg= e.message
            response
        }
    }
}