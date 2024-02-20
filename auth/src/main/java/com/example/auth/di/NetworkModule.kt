package com.example.auth.di

import com.core.data.Constants
import com.example.auth.data.remotesource.PhoneAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
 * Created by Sudhanshu Kumar on 18/02/24.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providePhoneAuthApi(): PhoneAuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BaseUrl.TWO_FACTOR)
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhoneAuthApi::class.java)
    }

}