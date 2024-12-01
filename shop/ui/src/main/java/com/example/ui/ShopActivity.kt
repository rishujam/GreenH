package com.example.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ui.databinding.ActivityShopBinding
import com.example.ui.listing.PlantsListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flTest, PlantsListFragment())
            commit()
        }
    }

}