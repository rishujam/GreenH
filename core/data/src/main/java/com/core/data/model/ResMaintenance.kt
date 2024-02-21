package com.core.data.model

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

data class ResMaintenance(
    val success: Boolean,
    val message: String? = null,
    val maintenance: Boolean = false
)
