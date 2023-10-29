package com.ev.greenh.common.commondata

import com.ev.greenh.common.commondata.api.MockyTestApi
import com.ev.greenh.common.commondata.api.PlantNetApi
import com.ev.greenh.common.commondata.api.RazorpayApi
import com.ev.greenh.common.commondata.api.VisionApi
import com.ev.greenh.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

object RetrofitPool {

    private val retroMap = hashMapOf<ApiIdentifier, ApiBase<*>>()

    fun getApi(identifier: ApiIdentifier): ApiBase<*> {
        return try {
            retroMap[identifier]!!
        } catch (_: Exception) {
            createApi(identifier)
        }
    }

    private fun createApi(identifier: ApiIdentifier): ApiBase<*> {
        val client = getClient(identifier)
        val retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl(identifier))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val api: ApiBase<*> = when (identifier) {
            is ApiIdentifier.MockyTestApi -> {
                ApiBase(retrofit.create(MockyTestApi::class.java))
            }

            is ApiIdentifier.VisionApi -> {
                ApiBase(retrofit.create(VisionApi::class.java))
            }

            is ApiIdentifier.PlantNetApi -> {
                ApiBase(retrofit.create(PlantNetApi::class.java))
            }

            is ApiIdentifier.Razorpay -> {
                ApiBase(retrofit.create(RazorpayApi::class.java))
            }
        }
        retroMap[identifier] = api
        return api
    }

    private fun getBaseUrl(identifier: ApiIdentifier): String {
        return when (identifier) {
            is ApiIdentifier.MockyTestApi -> {
                Constants.MOCKY_BASE_URL
            }

            is ApiIdentifier.VisionApi -> {
                Constants.GOOGLE_VISION_BASE_URL
            }

            is ApiIdentifier.PlantNetApi -> {
                Constants.PLANT_NET_BASE_URL
            }

            is ApiIdentifier.Razorpay -> {
                Constants.RAZORPAY_BASE_URL
            }
        }
    }

    private fun getClient(identifier: ApiIdentifier): OkHttpClient {
        return when (identifier) {
            is ApiIdentifier.MockyTestApi -> {
                OkHttpClient.Builder().build()
            }

            is ApiIdentifier.VisionApi -> {
                OkHttpClient.Builder().build()
            }

            is ApiIdentifier.PlantNetApi -> {
                OkHttpClient.Builder().build()
            }

            is ApiIdentifier.Razorpay -> {
                OkHttpClient.Builder().build()
            }
        }
    }

}