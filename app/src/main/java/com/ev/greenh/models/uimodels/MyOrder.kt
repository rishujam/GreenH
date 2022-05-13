package com.ev.greenh.models.uimodels

data class MyOrder(
    val orderId:String="",
    var plantPhoto:String="",
    var plantName:String="",
    val deliveryStatus:String="",
    val deliveryDate:String="",
    val orderedDate:String=""
)
