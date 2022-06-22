package com.ev.greenh.models

data class Profile(
    val emailId:String="",
    val name:String="",
    val address:String="",
    val phone:String="",
    val version:Int=1,
    val uid:String="",
    val gender:String="",
    val ageGroup:String="",
    val profileComplete:Boolean = false
)
