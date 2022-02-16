package com.ev.greenh.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.MainActivity
import com.ev.greenh.R
import com.ev.greenh.databinding.ActivityAuthBinding
import com.ev.greenh.firebase.AuthSource
import com.ev.greenh.localdatastore.UserPreferences
import com.ev.greenh.repository.AuthRepository
import com.ev.greenh.viewmodels.AuthViewModel
import com.ev.greenh.viewmodels.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth
    lateinit var viewModel: AuthViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        val signupFragment = SignupFragment()
        setCurrentFragment(signupFragment)

        //Setting viewModel
        auth = FirebaseAuth.getInstance()
        val authSource = AuthSource(auth)
        val repo = AuthRepository(authSource,userPreferences)
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

    private fun checkLoginState(){
        if(auth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}