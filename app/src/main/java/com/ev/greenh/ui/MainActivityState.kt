package com.ev.greenh.ui

import com.core.data.model.Feature

/*
 * Created by Sudhanshu Kumar on 13/02/24.
 */

data class MainActivityState(
    val isToShowUpdate: Boolean = false,
    val isToShowMaintenance: Boolean = false,
    val featureConfig: Map<String, Feature?> = mutableMapOf()
)
