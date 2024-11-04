package com.ev.greenh

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.core.ui.hide
import com.core.ui.nav.Navigation
import com.ev.greenh.databinding.ActivityLauncherBinding
import com.ev.greenh.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding
    private val viewModel: LauncherActivityViewModel by viewModels()

    @Inject
    lateinit var navigation: Navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreen()
        lifecycleScope.launch {
            viewModel.appInitialisation.collect {
                binding.ivAppLauncher.hide()
                if(it.isLoggedIn) {
                    onLoggedIn()
                } else {
                    navigation.homeActivity(this@LauncherActivity)
                }
            }
        }
        viewModel.getRecipeForStartUp()
    }

    private fun setDisplayMode() {
        val nightModeFlags: Int = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )

            Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

            Configuration.UI_MODE_NIGHT_UNDEFINED -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            )
        }
    }

    private fun onLoggedIn() {
        startActivity(
            Intent(
                this@LauncherActivity,
                MainActivity::class.java
            )
        )
        finish()
    }

    private fun fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(
                WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
            )
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

}