package com.ev.greenh.grow.ui.model

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

data class GrowDetailData(
    val headImageUrl: String,
    val plantName: String,
    val requirements: List<BadgeIconData>,
    val steps: List<String>,
    val isAvailableInShop: Boolean
)