package com.ev.greenh.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.*
import com.ev.greenh.databinding.ActivityMainBinding
import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.grow.ui.GrowFragment
import com.ev.greenh.home.HomeFragment
import com.ev.greenh.plantidentification.ui.PlantIndentifierActivity
import com.ev.greenh.repository.PlantRepository
import com.ev.greenh.ui.order.MyOrdersFragment
import com.ev.greenh.ui.plants.PlantFragment
import com.ev.greenh.ui.profile.SettingFragment
import com.ev.greenh.viewmodels.PlantViewModel
import com.ev.greenh.viewmodels.ViewModelFactory
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener

class MainActivity : AppCompatActivity(), PaymentResultWithDataListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: PlantViewModel
    var successListener=""
    var paymentData:PaymentData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val homeFragment = HomeFragment()
        setCurrentFragment(homeFragment)

        //Setting viewModel
        val plantSource = FirestoreSource()
        val repo = PlantRepository(plantSource,(application as GreenApp).userPreferences)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this,factory)[PlantViewModel::class.java]
        //end setting up viewModel

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.imHome -> {
                    setCurrentFragment(homeFragment)
                }
                R.id.imScanner -> openIdentificationScreen()
                R.id.imSetting -> {
                    val settingFragment = SettingFragment()
                    setCurrentFragmentBack(settingFragment)
                }
            }
            true
        }
    }

    private fun openIdentificationScreen() {
        startActivity(Intent(this, PlantIndentifierActivity::class.java))
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

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        when(p0){
            Checkout.NETWORK_ERROR -> Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
            Checkout.INVALID_OPTIONS -> Toast.makeText(this, "INVALID Parameters", Toast.LENGTH_SHORT).show()
            Checkout.PAYMENT_CANCELED -> Toast.makeText(this, "Payment Cancelled", Toast.LENGTH_SHORT).show()
            Checkout.TLS_ERROR -> Toast.makeText(this, "Your device is not supported", Toast.LENGTH_SHORT).show()
        }
        successListener ="N"
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        paymentData = p1
        successListener ="Y"
        Log.e("MainActivity: $p0", p1.toString())
    }

    fun hideNav(){
        binding.bottomNavigationView.visibility = View.GONE
    }
    fun viewNav(){
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        Log.e("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity", "onDestroy")
    }
}