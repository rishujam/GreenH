package com.ev.greenh.launcher

/*
 * Created by Sudhanshu Kumar on 07/11/24.
 */

sealed class LauncherEvent {

    object Skip : LauncherEvent()
    object SignIn : LauncherEvent()
    object ConfigLoaded : LauncherEvent()

}