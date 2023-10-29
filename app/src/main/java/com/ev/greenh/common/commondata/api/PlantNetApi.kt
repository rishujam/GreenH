package com.ev.greenh.common.commondata.api

import com.ev.greenh.PConstants
import com.ev.greenh.common.commondata.plantidentifyres.PlantIdentifyRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
 * Created by Sudhanshu Kumar on 29/10/23.
 */

interface PlantNetApi {

    @GET("/v2/identify/")
    fun identifyPlant(
        @Path("project") project: String = "all",
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