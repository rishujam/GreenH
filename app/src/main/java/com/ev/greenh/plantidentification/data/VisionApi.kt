package com.ev.greenh.plantidentification.data

import com.ev.greenh.plantidentification.data.model.req.ImageAnnotationRequest
import com.ev.greenh.plantidentification.data.model.res.AnnotateImageResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

interface VisionApi {

    @POST("v1/images:annotate")
    fun annotateImages(@Body request: ImageAnnotationRequest): Call<AnnotateImageResponse>

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://vision.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val visionApiService = retrofit.create(VisionApi::class.java)
    }

}