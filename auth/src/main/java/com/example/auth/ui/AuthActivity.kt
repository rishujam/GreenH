package com.example.auth.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.core.data.Constants
import com.core.ui.nav.Navigation
import com.example.auth.R
import com.example.auth.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val version = intent.getIntExtra(Constants.Args.BUILD_VERSION, 0)
        val bundle = Bundle()
        bundle.putInt(Constants.Args.BUILD_VERSION, version)
        val signupFragment = SignUpFrag()
        signupFragment.arguments = bundle
        setCurrentFragment(signupFragment)
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flAuth, fragment)
            commit()
        }

}