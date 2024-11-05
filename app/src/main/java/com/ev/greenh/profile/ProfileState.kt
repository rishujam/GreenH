package com.ev.greenh.profile

import com.example.auth.data.model.UserProfile

/*
 * Created by Sudhanshu Kumar on 29/10/24.
 */
data class ProfileState(
    val isLoggedIn: Boolean? = null,
    val isLoading: Boolean? = null,
    val profile: UserProfile? = null,
    val errorMsg: String? = null
)