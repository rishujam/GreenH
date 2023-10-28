package com.ev.greenh.common.commondata.api

import com.ev.greenh.models.RazorpayOrderId
import retrofit2.http.*

interface RazorpayApi {

    @POST("/createorder")
    suspend fun generateOrderId(@Body body:HashMap<String,Int>):RazorpayOrderId

}