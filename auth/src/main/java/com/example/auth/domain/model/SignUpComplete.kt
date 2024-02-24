package com.example.auth.domain.model

/*
 * Created by Sudhanshu Kumar on 23/02/24.
 */

data class SignUpComplete(
    val savedUserDataOnServer: Boolean? = null,
    val newUser: Boolean

)
