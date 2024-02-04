package com.ev.greenh.auth

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.ev.greenh.R
import com.ev.greenh.auth.ui.SignUpFrag
import com.ev.greenh.databinding.ActivityAuthBinding
import com.ev.greenh.ui.MainActivity
import com.example.auth.ui.TestFragment
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityAuthBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        setContentView(binding.root)
        val signupFragment = TestFragment()
        setCurrentFragment(signupFragment, R.id.flAuth)
    }

    fun setCurrentFragment(fragment: Fragment, frame: Int)=
        supportFragmentManager.beginTransaction().apply {
            replace(frame,fragment)
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