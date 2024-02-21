package com.ev.greenh

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.core.data.Constants
import com.core.ui.hide
import com.ev.greenh.databinding.ActivityLauncherBinding
import com.ev.greenh.ui.MainActivity
import com.example.auth.ui.ParentCallback
import com.example.auth.ui.SignUpFrag
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding
    private val viewModel: LauncherActivityViewModel by viewModels()
    private val buildVersion by lazy {
        BuildConfig.VERSION_CODE
    }

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
                    val bundle = Bundle()
                    bundle.putInt(Constants.Args.BUILD_VERSION, buildVersion)
                    val signupFragment = SignUpFrag(object : ParentCallback {
                        override fun onSignUpSuccess() {
                            onLoggedIn()
                        }
                    })
                    withContext(Dispatchers.Main) {
                        signupFragment.arguments = bundle
                        setCurrentFragment(signupFragment)
                    }
                }
            }
        }
        viewModel.getRecipeForStartUp()
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flAuth, fragment)
            commit()
        }


    fun setCurrentFragmentBack(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flAuth, fragment)
            addToBackStack("b")
            commit()
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