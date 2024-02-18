package com.example.auth.data.model

/*
 * Created by Sudhanshu Kumar on 09/02/24.
 */

data class ResUserExist(
    val success: Boolean,
    val exist: Boolean? = null,
    val userProfile: UserProfile? = null,
    val msg: String? = null
)
