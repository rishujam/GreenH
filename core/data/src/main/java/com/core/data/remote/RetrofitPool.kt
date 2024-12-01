package com.core.data.remote

import com.core.util.Constants
import com.core.data.model.ApiBase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = ApiBase(retrofit.create(apiClass))
        retroMap[identifier] = api
        return api
    }

    private fun getBaseUrl(identifier: ApiIdentifier): String {
        return when (identifier) {
            is ApiIdentifier.MockyTestApi -> {
                Constants.BaseUrl.MOCKY
            }

            is ApiIdentifier.PlantNetApi -> {
                Constants.BaseUrl.PLANT_NET
            }

            is ApiIdentifier.Razorpay -> {
                Constants.BaseUrl.RAZORPAY
            }

            is ApiIdentifier.TwoFactor -> {
                Constants.BaseUrl.TWO_FACTOR
            }
        }
    }

    private fun getClient(identifier: ApiIdentifier): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return when (identifier) {
            is ApiIdentifier.MockyTestApi -> {
                OkHttpClient.Builder().addInterceptor(interceptor).build()
            }

            is ApiIdentifier.PlantNetApi -> {
                OkHttpClient.Builder().addInterceptor(interceptor).build()
            }

            is ApiIdentifier.Razorpay -> {
                OkHttpClient.Builder().addInterceptor(interceptor).build()
            }

            is ApiIdentifier.TwoFactor -> {
                OkHttpClient.Builder().addInterceptor(interceptor).build()
            }
        }
    }

}