package com.ev.greenh.common.commondata.model

import com.ev.greenh.BuildConfig

/*
 * Created by Sudhanshu Kumar on 15/11/23.
 */

data class AppConfig(
    val forceUpdate: Boolean,
    val latestVersion: Int = BuildConfig.VERSION_CODE,
    val maintenance: Boolean
)