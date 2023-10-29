package com.ev.greenh.plantidentify.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class Product(
    val name: String,
    val displayName: String,
    val description: String,
    val productCategory: String,
    val productLabels: List<KeyValue>
)