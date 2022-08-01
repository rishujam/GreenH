package com.ev.greenh.auth

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.GreenApp
import com.ev.greenh.R
import com.ev.greenh.databinding.ActivityAuthBinding
import com.ev.greenh.firebase.AuthSource
import com.ev.greenh.repository.AuthRepository
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.viewmodels.AuthViewModel
import com.ev.greenh.viewmodels.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth
    lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signupFragment = SignupFragment()
        setCurrentFragment(signupFragment)

        //Setting viewModel
        auth = FirebaseAuth.getInstance()
        val authSource = AuthSource(auth)
        val repo = AuthRepository(authSource,(application as GreenApp).userPreferences)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory)[AuthViewModel::class.java]
        //end setting up viewModel
    }

    fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flAuth,fragment)
            commit()
        }


    fun setCurrentFragmentBack(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flAuth,fragment)
            addToBackStack("b")
            commit()
        }


    override fun onStart() {
        super.onStart()
        checkLoginState()
    }

    private fun setDisplayMode(){
        val nightModeFlags: Int = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Configuration.UI_MODE_NIGHT_UNDEFINED -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun checkLoginState(){
        if(auth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}