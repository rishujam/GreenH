package com.ev.greenh.grow.ui.model

import com.core.ui.model.RadioBtnOption

/*
 * Created by Sudhanshu Kumar on 21/10/23.
 */

data class LocalPlantListQuestionItem(
    val id: String,
    val heading: String,
    val question: String,
    val radioBtnOptions: List<RadioBtnOption>,
    override val viewType: Int
) : LocalPlantListItemBase