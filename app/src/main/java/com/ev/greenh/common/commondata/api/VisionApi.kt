package com.ev.greenh.common.commondata.api

import com.ev.greenh.plantidentify.data.model.req.ImageAnnotationRequest
import com.ev.greenh.plantidentify.data.model.res.AnnotateImageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/*
 * Created by Sudhanshu Kumar on 10/10/23.
 */

interface VisionApi {

    @POST("v1/images:annotate")
    fun annotateImages(@Body request: ImageAnnotationRequest): Call<AnnotateImageResponse>

}