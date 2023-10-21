package com.ev.greenh.grow.ui.model

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

data class LocalPlantListQuestionItem(
    val id: String,
    val heading: String,
    val question: String,
    val options: List<Option>,
    override val viewType: Int
) : LocalPlantListItemBase