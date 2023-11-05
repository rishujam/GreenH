package com.ev.greenh.common.commondata.plantidentifyres

import com.ev.greenh.plantidentify.data.model.res.Query
import com.ev.greenh.plantidentify.data.model.res.Result
import com.google.gson.annotations.SerializedName

data class PlantIdentifyRes(
    @SerializedName("bestMatch")
    val bestMatch: String,
    val language: String,
    @SerializedName("preferedReferential")
    val preferedReferential: String,
    val query: Query,
    @SerializedName("remainingIdentificationRequests")
    val remainingIdentificationRequests: Int,
    val results: List<Result>,
    @SerializedName("switchToProject")
    val switchToProject: String,
    val version: String
)