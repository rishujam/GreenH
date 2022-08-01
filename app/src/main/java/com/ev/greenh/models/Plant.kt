package com.ev.greenh.models

data class Plant(
    val id:String="",
    val name:String="",
    var imageLocation:String="", //var so that we can replace the HQ image after fetching the actual plant in FirestoreSource file
    val price:String="",
    val store:String="",
    val description:String="",
    val height:String="",
    val status:String="",
    val featureNo:Int=1000,
    val videoLink:String="",
    val water:String="",
    val sunlight:String="",
    val category:String=""
)