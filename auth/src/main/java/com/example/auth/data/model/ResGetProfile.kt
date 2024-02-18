package com.example.auth.data.model

/*
 * Created by Sudhanshu Kumar on 07/02/24.
 */

data class ResGetProfile(
    val success: Boolean,
    val msg: String? = null,
    val profile: UserProfile? = null
)
