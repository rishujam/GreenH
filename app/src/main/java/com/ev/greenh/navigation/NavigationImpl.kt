package com.ev.greenh.navigation

import android.app.Activity
import android.content.Intent
import com.core.data.Constants
import com.core.ui.nav.Navigation
import com.ev.greenh.ui.MainActivity
import com.example.auth.ui.AuthActivity
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 25/04/24.
 */

class NavigationImpl @Inject constructor() : Navigation {

    override fun homeActivity(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }

    override fun authActivity(activity: Activity, buildVersion: Int) {
        val intent = Intent(activity, AuthActivity::class.java).apply {
            putExtra(Constants.Args.BUILD_VERSION, buildVersion)
        }
        activity.startActivity(intent)
    }
}