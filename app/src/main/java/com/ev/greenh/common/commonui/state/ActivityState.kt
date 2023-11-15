package com.ev.greenh.common.commonui.state

import com.ev.greenh.common.commonui.model.DialogModel

/*
 * Created by Sudhanshu Kumar on 14/11/23.
 */

data class ActivityState(
    val features: Map<String, Boolean>,
    val showDialog: DialogModel? = null,
    val maintenance: Boolean = false,
    val isUpdateRequired: Boolean = false
)