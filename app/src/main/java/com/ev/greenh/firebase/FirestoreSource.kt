package com.ev.greenh.firebase

import com.ev.greenh.models.*
import com.ev.greenh.models.uimodels.MyOrder
import com.ev.greenh.models.uimodels.MyOrderDetail
import com.ev.greenh.models.uimodels.PlantMyOrder
import com.ev.greenh.util.Constants
import com.ev.greenh.util.Constants.QUERY_PAGE_SIZE
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreSource @Inject constructor() {

    private val fireRef = Firebase.firestore

    suspend fun getAllPlants(collection: String, page: Int, lastFeatureNo: Int): Plants {
        val list = mutableListOf<Plant>()
        val data = if (lastFeatureNo == 0) {
            fireRef.collection(collection).whereEqualTo("status", "Available").orderBy("featureNo")
                .startAfter(page * 5).limit(5).get().await()
        } else {
            fireRef.collection(collection).whereEqualTo("status", "Available").orderBy("featureNo")
                .startAfter(lastFeatureNo).limit(5).get().await()
        }
        for (i in data.documents) {
            val plant = i.toObject<Plant>()
            if (plant != null) {
                list.add(plant)
            }
        }
        return Plants(list)
    }

    suspend fun getAllPlantsPaging(page: Int): Plants {
        val list = mutableListOf<Plant>()
        val startAfter = QUERY_PAGE_SIZE * (page - 1)
        val data = fireRef
            .collection("plant_sample_ref")
            .whereEqualTo("status", "Available")
            .orderBy("featureNo")
            .startAfter(startAfter)
            .limit(QUERY_PAGE_SIZE.toLong()).get().await()
        for (i in data.documents) {
            val plant = i.toObject<Plant>()
            plant?.let { list.add(it) }
        }
        return Plants(list)
    }

    suspend fun getPlantsByCategory(
        collection: String,
        category: String,
        lastFeatureNo: Int
    ): Plants {
        val list = mutableListOf<Plant>()
        val data = if (lastFeatureNo == 0) {
            fireRef.collection(collection).whereEqualTo("status", "Available")
                .whereEqualTo("category", category).orderBy("featureNo").limit(5).get().await()
        } else {
            fireRef.collection(collection).whereEqualTo("status", "Available")
                .whereEqualTo("category", category).orderBy("featureNo").startAfter(lastFeatureNo)
                .limit(5).get().await()
        }
        for (i in data.documents) {
            val plant = i.toObject<Plant>()
            if (plant != null) {
                list.add(plant)
            }
        }
        return Plants(list)
    }

    suspend fun getSinglePlant(collection: String, id: String): Plant {
        val ref = fireRef.collection(collection).document(id).get().await()
        return ref.toObject<Plant>() ?: Plant()
    }

    suspend fun addPlantToBag(
        plantId: String,
        user: String,
        collection: String,
        quantity: String
    ): Response {
        //checking if same plant exist
        var q = quantity.toInt()
        val ref = fireRef.collection(collection).document(user).get().await()
        val data = ref.data
        if (data != null) {
            if (data.keys.contains(plantId)) {
                q += ref[plantId].toString().toInt()
            }
        }
        //adding quantity
        fireRef.collection(collection).document(user)
            .set(mapOf(plantId to q.toString()), SetOptions.merge()).await()
        return Response(true)
    }

    suspend fun updateQuantity(
        user: String,
        collection: String,
        newQuantity: Int,
        plantId: String
    ): Response {
        val res = Response()
        val ref = fireRef.collection(collection).document(user)
        Firebase.firestore.runTransaction { transaction ->
            transaction.update(ref, plantId, newQuantity.toString())
            null
        }.addOnSuccessListener {
            res.success = true
        }.addOnFailureListener {
            res.errorMsg = it.message
        }.await()
        return res
    }

    suspend fun getBagItems(collBag: String, collPlant: String, user: String): Map<Plant, String> {
        val map = mutableMapOf<Plant, String>()
        val ref = fireRef.collection(collBag).document(user).get().await()
        val data = ref.data
        if (data != null) {
            coroutineScope {
                for (i in data.keys) {
                    async {
                        val plant = getSinglePlant(collPlant, i)
                        val quantity = ref[plant.id].toString()
                        val price = plant.price.toInt() * quantity.toInt()
                        map[plant] = "$quantity,$price"
                    }
                }
            }
        }
        return map
    }

    suspend fun deleteItemFromBag(user: String, collection: String, plantId: String): Response {
        val res = Response()
        val docRef = fireRef.collection(collection).document(user)
        val updates = hashMapOf<String, Any>(
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

    suspend fun placeOrder(order: Order, collection: String): Response {
        val response = Response()
        return try {
            fireRef.collection(collection).document(order.orderId).set(order).await()
            response.success = true
            response
        } catch (e: Exception) {
            response.errorMsg = e.message
            response
        }
    }

    suspend fun getUserOrders(
        user: String,
        collectionOrder: String,
        collectionPlant: String
    ): List<MyOrder> {
        val out = mutableListOf<MyOrder>()
        val snap = fireRef.collection(collectionOrder).get().await()
        for (i in snap.documents) {
            val order = i.toObject<Order>()
            if (order != null && order.user == user) {
                for (plantId in order.items) {
                    val myOrder = MyOrder(
                        orderId = order.orderId,
                        deliveryStatus = order.deliveryStatus,
                        deliveryDate = order.dateDelivered,
                        orderedDate = order.dateOrdered
                    )
                    val plant = getSinglePlant(collectionPlant, plantId.split(",")[0])
                    myOrder.plantName = plant.name
                    myOrder.plantPhoto = plant.imageLocation
                    out.add(myOrder)
                }
            }
        }
        return out
    }

    suspend fun getSingleOrderDetails(
        orderId: String,
        collectionOrder: String,
        collectionPlant: String
    ): MyOrderDetail {
        val snap = fireRef.collection(collectionOrder).document(orderId).get().await()
        val order = snap.toObject<Order>()!!
        val listOfPlantMyOrder = mutableListOf<PlantMyOrder>()
        for (i in order.items) {
            val plantId = i.split(",")[0]
            val quantity = i.split(",")[1]
            val plant = getSinglePlant(collectionPlant, plantId)
            val plantMyOrder =
                PlantMyOrder(plantId, plant.name, plant.imageLocation, plant.price, quantity)
            listOfPlantMyOrder.add(plantMyOrder)
        }
        val itemsAmount = (order.totalAmount.toInt() - order.deliveryCharges.toInt()).toString()
        return MyOrderDetail(
            order.dateOrdered,
            orderId,
            order.totalAmount,
            order.deliveryStatus,
            order.dateDelivered,
            listOfPlantMyOrder,
            order.paymentId,
            order.address,
            order.phone,
            itemsAmount,
            order.deliveryCharges
        )
    }

    suspend fun emptyUserCart(user: String, collection: String): Response {
        val response = Response()
        return try {
            fireRef.collection(collection).document(user).delete().await()
            response.success = true
            response
        } catch (e: Exception) {
            response.errorMsg = e.message
            response
        }
    }

    suspend fun getBagItemIds(email: String, collection: String): List<String> {
        val list = mutableListOf<String>()
        val snap = fireRef.collection(collection).document(email).get().await()
        val data = snap.data
        if (data != null) {
            for (i in data) {
                list.add("${i.key},${i.value}")
            }
        }
        return list
    }

    suspend fun updateUserDetails(collection: String, profile: Profile): Response {
        val response = Response()
        return try {
            fireRef.collection(collection).document(profile.uid).set(profile).await()
            response.success = true
            response
        } catch (e: Exception) {
            response.errorMsg = e.message
            response
        }
    }

    suspend fun sendCancelRequest(orderId: String, collection: String): Response {
        val response = Response()
        val docRef = fireRef.collection(collection).document(orderId)
        val updates = hashMapOf<String, Any>(
            "deliveryStatus" to "Cancel Requested"
        )
        docRef.update(updates).addOnSuccessListener {
            response.success = true
        }.addOnFailureListener {
            response.errorMsg = it.message
        }.await()
        return response
    }

    suspend fun getPlantVideoUrl(collection: String, plantId: String): String {
        val urlSnap = fireRef.collection(collection).document(plantId).get().await()
        return urlSnap["url"].toString()
    }

    suspend fun getNotifyToken(uid: String, collection: String): String {
        val snap = fireRef.collection(collection).document(uid).get().await()
        return snap["token"].toString()
    }

    suspend fun getApiKey(collection: String): String {
        val snap = fireRef.collection(collection).document("razorpay").get().await()
        return snap["api_key"].toString()
    }

    suspend fun getMinVersionToRun(collection: String): Int {
        val snap = fireRef.collection(collection).document("minVersion").get().await()
        return snap["v"].toString().toInt()
    }
}