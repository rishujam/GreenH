package com.example.admin.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.admin.R
import com.example.admin.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCurrentFragment(AdminHomeFrag())
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flAdmin, fragment)
            commit()
        }

    fun setCurrentFragmentBack(fragment: Fragment, tag: String? = null) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flAdmin, fragment, tag)
            addToBackStack("b")
            commit()
        }

}