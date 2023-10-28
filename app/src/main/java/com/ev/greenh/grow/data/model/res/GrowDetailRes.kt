package com.ev.greenh.grow.data.model.res

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

data class GrowDetailRes(
    val headImageUrl: String,
    val plantName: String,
    val requirements: Map<String, String>,
    val steps: List<String>,
    val isAvailableInShop: Boolean
)