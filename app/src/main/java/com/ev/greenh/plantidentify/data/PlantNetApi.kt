package com.ev.greenh.plantidentify.data

import com.ev.greenh.PConstants
import com.ev.greenh.plantidentify.data.model.res.PlantIdentifyRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

interface PlantNetApi {

    @GET("/v2/identify/all")
    suspend fun identifyPlant(
        @Query("images") images: List<String>,
        @Query("organs") organs: List<String>,
        @Query("include-related-images") includeRelatedImages: Boolean = false,
        @Query("no-reject") noReject: Boolean = false,
        @Query("lang") lang: String = "en",
        @Query("type") type: String = "kt",
        @Query("api-key") apiKey: String = PConstants.PLANT_IDENTIFY_API_KEY,
        @Query("authenix-access-token") accessToken: String? = null
    ): Response<PlantIdentifyRes>

}