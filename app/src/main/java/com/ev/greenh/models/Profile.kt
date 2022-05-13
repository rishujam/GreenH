package com.ev.greenh.models

data class Profile(
    val emailId:String="",
    val name:String="",
    val address:String="",
    val phone:String="",
    val profileComplete:Boolean = false
)
