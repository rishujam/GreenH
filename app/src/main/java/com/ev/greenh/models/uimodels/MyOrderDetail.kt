package com.ev.greenh.models.uimodels

data class MyOrderDetail(
    val orderDate:String,
    val orderId:String,
    val orderTotal:String,
    val orderStatus:String,
    val deliveryDate:String,
    val items:List<PlantMyOrder>,
    val paymentId:String,
    val address:String,
    val phone:String,
    val itemsAmount:String,
    val deliveryCharge:String
)
