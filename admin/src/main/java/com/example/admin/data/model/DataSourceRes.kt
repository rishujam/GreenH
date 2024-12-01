package com.example.admin.data.model

/*
 * Created by Sudhanshu Kumar on 24/11/24.
 */

data class DataSourceRes(
    val isSuccessful: Boolean,
    val message: String? = null,
    val data: Any? = null
)