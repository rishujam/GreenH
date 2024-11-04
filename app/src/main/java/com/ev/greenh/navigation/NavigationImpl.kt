package com.ev.greenh.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.core.data.Constants
import com.core.ui.nav.Navigation
import com.ev.greenh.ui.MainActivity
import com.example.auth.ui.AuthActivity
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 25/04/24.
 */

class NavigationImpl @Inject constructor() : Navigation {

    override fun homeActivity(context: Context?) {
        val intent = Intent(context, MainActivity::class.java)
        context?.startActivity(intent)
    }

    override fun authActivity(
        context: Context?,
        buildVersion: Int,
        activityLauncher: ActivityResultLauncher<Intent>?
    ) {
        activityLauncher?.let {
            val intent = Intent(context, AuthActivity::class.java)
            intent.putExtra(Constants.Args.BUILD_VERSION, buildVersion)
            activityLauncher.launch(intent)
        } ?: run {
            val intent = Intent(context, AuthActivity::class.java).apply {
                putExtra(Constants.Args.BUILD_VERSION, buildVersion)
            }
            context?.startActivity(intent)
        }
    }
}