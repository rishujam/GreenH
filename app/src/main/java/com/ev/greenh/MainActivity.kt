package com.ev.greenh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.databinding.ActivityMainBinding
import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.localdatastore.UserPreferences
import com.ev.greenh.repository.PlantRepository
import com.ev.greenh.viewmodels.PlantViewModel
import com.ev.greenh.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: PlantViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        val plantFragment = PlantFragment()
        setCurrentFragment(plantFragment)

        //Setting viewModel
        val plantSource = FirestoreSource()
        val repo = PlantRepository(plantSource,userPreferences)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory)[PlantViewModel::class.java]
        //end setting up viewModel


        val myOrdersFragment = MyOrdersFragment()
        val settingFragment = SettingFragment()
        val bagFragment = BagFragment()
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.imPlants -> setCurrentFragment(plantFragment)
                R.id.imOrder ->setCurrentFragment(myOrdersFragment)
                R.id.imSetting -> setCurrentFragment(settingFragment)
                R.id.imBag -> setCurrentFragment(bagFragment)
            }
            true
        }
    }

    fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flHome,fragment)
            commit()
        }

    fun setCurrentFragmentBack(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flHome,fragment)
            addToBackStack("b")
            commit()
        }
}