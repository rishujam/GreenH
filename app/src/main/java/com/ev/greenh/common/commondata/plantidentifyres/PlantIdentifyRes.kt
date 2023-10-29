package com.ev.greenh.common.commondata.plantidentifyres

data class PlantIdentifyRes(
    val bestMatch: String,
    val language: String,
    val preferedReferential: String,
    val query: Query,
    val remainingIdentificationRequests: Int,
    val results: List<Result>,
    val switchToProject: String,
    val version: String
)