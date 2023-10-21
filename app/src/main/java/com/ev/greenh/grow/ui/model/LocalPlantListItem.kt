package com.ev.greenh.grow.ui.model

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

data class LocalPlantListItem(
    val id: String,
    val name: String,
    val imageUrl: String,
    override val viewType: Int
) : LocalPlantListItemBase
