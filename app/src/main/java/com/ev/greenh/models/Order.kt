package com.ev.greenh.models

data class Order(
    val orderId:String="",
    val user:String="",
    val items:List<String> = mutableListOf(), //List ot items in order (Plant id, quantity)
    val dateOrdered:String="",
    val deliveryCharges:String="",
    val totalAmount:String="",
    var deliveryStatus:String="",
    val dateDelivered:String="",
    val paymentId:String="",
    val address:String="",
    val phone:String=""
)