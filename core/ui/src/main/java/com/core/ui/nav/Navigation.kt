package com.core.ui.nav

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

/*
 * Created by Sudhanshu Kumar on 25/04/24.
 */

interface Navigation {

    fun launcherActivity(context: Context?)

    fun homeActivity(context: Context?)

    fun authActivity(
        context: Context?,
        buildVersion: Int,
        activityLauncher: ActivityResultLauncher<Intent>? = null
    )

    fun shopActivity(context: Context?)

}