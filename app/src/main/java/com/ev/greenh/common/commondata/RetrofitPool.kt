package com.ev.greenh.common.commondata

import com.ev.greenh.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

object RetrofitPool {

    private val retroMap = hashMapOf<ApiIdentifier, ApiBase<*>>()

    fun <T>getApi(identifier: ApiIdentifier, apiClass: Class<T>): ApiBase<*> {
        return try {
            retroMap[identifier]!!
        } catch (_: Exception) {
            createApi(identifier, apiClass)
        }
    }

    private fun <T>createApi(identifier: ApiIdentifier, apiClass: Class<T>): ApiBase<*> {
        val client = getClient(identifier)
        val retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl(identifier))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val api = ApiBase(retrofit.create(apiClass))
        retroMap[identifier] = api
        return api
    }

    private fun getBaseUrl(identifier: ApiIdentifier): String {
        return when (identifier) {
            is ApiIdentifier.MockyTestApi -> {
                Constants.MOCKY_BASE_URL
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

            is ApiIdentifier.PlantNetApi -> {
                OkHttpClient.Builder().build()
            }

            is ApiIdentifier.Razorpay -> {
                OkHttpClient.Builder().build()
            }
        }
    }

}