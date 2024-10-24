package com.core.ui.nav

import android.app.Activity

/*
 * Created by Sudhanshu Kumar on 25/04/24.
 */

interface Navigation {

    fun homeActivity(activity: Activity)

    fun authActivity(activity: Activity, buildVersion: Int)

}