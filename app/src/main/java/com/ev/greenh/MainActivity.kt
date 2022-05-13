package com.ev.greenh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.databinding.ActivityMainBinding
import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.localdatastore.UserPreferences
import com.ev.greenh.repository.PlantRepository
import com.ev.greenh.viewmodels.PlantViewModel
import com.ev.greenh.viewmodels.ViewModelFactory
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultListener
import com.razorpay.PaymentResultWithDataListener

class MainActivity : AppCompatActivity(), PaymentResultWithDataListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: PlantViewModel
    private lateinit var userPreferences: UserPreferences
    var successListener=""
    var paymentData:PaymentData? = null

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
        Log.e("MainActivity: $p0", p1.toString())
        successListener ="Y"
    }

    fun setCurrentIcon(){
        binding.bottomNavigationView.selectedItemId = R.id.imOrder
    }
}