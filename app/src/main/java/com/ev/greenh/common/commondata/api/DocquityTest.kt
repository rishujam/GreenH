package com.ev.greenh.common.commondata.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

/*
 * Created by Sudhanshu Kumar on 31/01/24.
 */

interface DocquityTest {

    @GET("/profile/detail/")
    fun getProfile(
        @Header("userauthkey") userauthkey: String,
        @Header("ver") ver: String,
        @Header("appversion") appversion: String,
        @Header("lang") lang: String,
        @Header("devicetype") devicetype: String,
        @Header("Authorization") authorization: String,
        @Header("auth2") auth2: String,
        @Header("udid") udid: String,
        @Header("timezone") timezone: String,
        @Header("campaignid") campId: String,
        @Header("sessionId") sessionId: String,
        @Header("releaseVersion") releaseVersion: String,
        @Header("usersessiontoken") usersessiontoken: String,
        @Header("x-api-key") apiKey: String,
        @Header("version") version: String,
        @Header("refreshToken") refreshToken: String?,
        @Query("custom_id") customId: String
    ): Call<ResponseBody>

    @GET("api/v2/config/app")
    fun getAppConfig(
        @Header("content-type") contentType: String,
        @Header("releaseversion") releaseVersion: String,
        @Header("application-version") applicationVersion: String,
        @Header("usersessiontoken") userSessionToken: String,
        @Header("device-type") deviceType: String,
        @Header("country-code") countryCode: String
    ): Call<ResponseBody>

}