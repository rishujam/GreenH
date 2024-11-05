package com.example.auth.data.model

import java.io.Serializable

data class UserProfile(
    val emailId:String="",
    val name:String="",
    val address:String="",
    val phone:String="",
    val version:Int=1,
    val uid:String="",
    val gender:String="",
    val ageGroup:String="",
    val profileComplete:Boolean = false
) : Serializable
