package com.ev.greenh.razorpayapi

import com.ev.greenh.util.Constants.RAZORPAY_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RazorpayInstance {

    companion object{

        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(RAZORPAY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api by lazy {
            retrofit.create(RazorpayApi::class.java)
        }
    }
}