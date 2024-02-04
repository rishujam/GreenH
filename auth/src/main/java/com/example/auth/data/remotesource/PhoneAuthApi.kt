package com.example.auth.data.remotesource

import com.example.auth.data.model.ResSendOtp
import com.example.auth.data.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/*
 * Created by Sudhanshu Kumar on 21/01/24.
 */

interface PhoneAuthApi {


    @GET("API/V1/{api_key}/SMS/{phone_number}/AUTOGEN3/OTP1")
    suspend fun sendOtp(
        @Path("api_key") apiKey: String,
        @Path("phone_number") phoneNumber: String
    ): Response<ResSendOtp>

    companion object {
        private val client = OkHttpClient.Builder().build()
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api: PhoneAuthApi by lazy {
            retrofit.create(PhoneAuthApi::class.java)
        }
    }

}