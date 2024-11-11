package com.ev.greenh.launcher

/*
 * Created by Sudhanshu Kumar on 07/11/24.
 */

data class LauncherState(
    val isLoggedIn: Boolean? = null,
    val isLoginSkippedEarlier: Boolean? = null,
    val configLoaded: Boolean = false
)