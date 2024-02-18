package com.ev.greenh

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.core.data.AppConfigRepositoryImpl
import com.core.data.Constants
import com.core.data.localstorage.ConfigDatabase
import com.core.data.pref.ConfigPref
import com.core.data.remote.ConfigDataSource
import com.ev.greenh.databinding.ActivityAuthBinding
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.viewmodels.ViewModelFactory
import com.example.auth.ui.ParentCallback
import com.example.auth.ui.SignUpFrag
import kotlinx.coroutines.launch

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var viewModel: LauncherActivityViewModel
    val buildVersion by lazy {
        BuildConfig.VERSION_CODE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModelFactory = ViewModelFactory(context = this)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[LauncherActivityViewModel::class.java]
        lifecycleScope.launch {
            viewModel.appInitialisation.collect {
                val bundle = Bundle()
                bundle.putInt(Constants.Args.BUILD_VERSION, buildVersion)
                val signupFragment = SignUpFrag(object : ParentCallback {
                    override fun onSignUpSuccess() {
                        startActivity(
                            Intent(
                                this@LauncherActivity,
                                MainActivity::class.java
                            )
                        )
                    }
                })
                signupFragment.arguments = bundle
                setCurrentFragment(signupFragment)
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


    override fun onStart() {
        super.onStart()
        checkLoginState()
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

    private fun checkLoginState() {
        //TODO Check if logged in
        if (false) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}