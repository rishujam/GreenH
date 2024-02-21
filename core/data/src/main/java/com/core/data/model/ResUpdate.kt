package com.core.data.model

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

data class ResUpdate(
    val success: Boolean,
    val message: String? = null,
    val data: Update? = null
)