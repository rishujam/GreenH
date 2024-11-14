package com.ev.greenh.launcher

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.core.ui.nav.Navigation
import com.ev.greenh.BuildConfig
import com.ev.greenh.databinding.ActivityLauncherBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding
    private val viewModel: LauncherActivityViewModel by viewModels()

    @Inject
    lateinit var navigation: Navigation

    private val buildVersion by lazy {
        BuildConfig.VERSION_CODE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cvLauncher.setContent {
            LauncherScreen(state = viewModel.state) {
                when (it) {
                    is LauncherEvent.ConfigLoaded -> {
                        viewModel.onEvent(it)
                        if (
                            viewModel.state.isLoggedIn == true ||
                            viewModel.state.isLoginSkippedEarlier == true
                        ) {
                            navigateToHome()
                        }
                    }

                    is LauncherEvent.Skip -> {
                        viewModel.onEvent(it) {
                            if (viewModel.state.configLoaded) {
                                navigateToHome()
                            }
                        }
                    }

                    is LauncherEvent.SignIn -> {
                        if (viewModel.state.configLoaded) {
                            navigateToAuth()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        navigation.homeActivity(this@LauncherActivity)
        finish()
    }

    private fun navigateToAuth() {
        navigation.authActivity(
            this@LauncherActivity,
            buildVersion = buildVersion
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