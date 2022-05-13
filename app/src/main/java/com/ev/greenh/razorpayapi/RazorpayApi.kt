package com.ev.greenh.razorpayapi

import com.ev.greenh.models.RazorpayOrderId
import retrofit2.http.*

interface RazorpayApi {

    @POST("/createorder")
    suspend fun generateOrderId(@Body body:HashMap<String,Int>):RazorpayOrderId

}