package com.ev.greenh.navigation

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.core.util.Constants
import com.core.ui.nav.Navigation
import com.ev.greenh.launcher.LauncherActivity
import com.ev.greenh.ui.MainActivity
import com.example.admin.ui.AdminActivity
import com.example.auth.ui.AuthActivity
import com.example.ui.ShopActivity
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 25/04/24.
 */

class NavigationImpl @Inject constructor() : Navigation {

    override fun launcherActivity(context: Context?) {
        val intent = Intent(context, LauncherActivity::class.java)
        context?.startActivity(intent)
    }

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
            intent.putExtra(Constants.Args.START_FOR_RESULT, true)
            activityLauncher.launch(intent)
        } ?: run {
            val intent = Intent(context, AuthActivity::class.java).apply {
                putExtra(Constants.Args.BUILD_VERSION, buildVersion)
            }
            context?.startActivity(intent)
        }
    }

    override fun shopActivity(context: Context?) {
        context?.startActivity(Intent(context, ShopActivity::class.java))
    }

    override fun adminActivity(context: Context?) {
        context?.startActivity(Intent(context, AdminActivity::class.java))
    }
}