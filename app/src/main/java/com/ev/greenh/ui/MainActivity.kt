package com.ev.greenh.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ev.greenh.GreenApp
import com.ev.greenh.R
import com.ev.greenh.common.commondata.AppStartupRepository
import com.ev.greenh.common.commonui.ActivityViewModel
import com.ev.greenh.common.commonui.event.ActivityEvent
import com.ev.greenh.common.commonui.model.DialogModel
import com.ev.greenh.databinding.ActivityMainBinding
import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.home.ui.HomeFragment
import com.ev.greenh.repository.PlantRepository
import com.ev.greenh.ui.plants.PlantFragment
import com.ev.greenh.ui.profile.SettingFragment
import com.ev.greenh.util.Constants
import com.ev.greenh.viewmodels.PlantViewModel
import com.ev.greenh.viewmodels.ViewModelFactory
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), PaymentResultWithDataListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: PlantViewModel
    lateinit var activityViewModel: ActivityViewModel
    var successListener = ""
    var paymentData: PaymentData? = null
    var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val homeFragment = HomeFragment()
        setCurrentFragment(homeFragment)

        val plantSource = FirestoreSource()
        val repo = PlantRepository(plantSource, (application as GreenApp).userPreferences)
        val factory = ViewModelFactory(repo)
        val startupRepo = AppStartupRepository()
        val activityFactory = ViewModelFactory(startupRepo)
        activityViewModel = ViewModelProvider(this, activityFactory)[ActivityViewModel::class.java]
        viewModel = ViewModelProvider(this, factory)[PlantViewModel::class.java]

        activityViewModel.getConfigData()

        loadUid()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.imHome -> {
                    setCurrentFragment(homeFragment)
                }

                R.id.imShop -> {
                    if (activityViewModel.isFeatureEnabled(Constants.Feature.SHOP)) {
                        val fragment = PlantFragment()
                        setCurrentFragmentBack(fragment)
                    } else {
                        buildAlert(
                            { },
                            "Go Back",
                            "Feature Unavailable",
                            "The feature will soon be available",
                            true
                        )
                    }
                }

                R.id.imSetting -> {
                    val settingFragment = SettingFragment()
                    setCurrentFragmentBack(settingFragment)
                }
            }
            true
        }
    }

    fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flHome, fragment)
            commit()
        }

    fun setCurrentFragmentBack(fragment: Fragment, tag: String? = null) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flHome, fragment, tag)
            addToBackStack("b")
            commit()
        }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        when (p0) {
            Checkout.NETWORK_ERROR -> Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT)
                .show()

            Checkout.INVALID_OPTIONS -> Toast.makeText(
                this,
                "INVALID Parameters",
                Toast.LENGTH_SHORT
            ).show()

            Checkout.PAYMENT_CANCELED -> Toast.makeText(
                this,
                "Payment Cancelled",
                Toast.LENGTH_SHORT
            ).show()

            Checkout.TLS_ERROR -> Toast.makeText(
                this,
                "Your device is not supported",
                Toast.LENGTH_SHORT
            ).show()
        }
        successListener = "N"
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        paymentData = p1
        successListener = "Y"
        Log.e("MainActivity: $p0", p1.toString())
    }

    fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }
                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )
                    onPhotoTaken(rotatedBitmap)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.d("Camera", "Error photo: ${exception.message}")
                }
            }
        )
    }

    fun hideNav() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun viewNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    fun buildAlert(
        onBtnClick: () -> Unit,
        btnText: String,
        title: String,
        message: String,
        dismissOnBtnClick: Boolean
    ) {
        val dialog = Dialog(this)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_generic)
            val btn: Button = findViewById(R.id.btnDialog)
            val titleView: TextView = findViewById(R.id.tvDialogTitle)
            val messageView: TextView = findViewById(R.id.tvDialogMessage)
            show()
            titleView.text = title
            messageView.text = message
            btn.text = btnText
            setCancelable(false)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            btn.setOnClickListener {
                onBtnClick()
                if(dismissOnBtnClick) dismiss()
            }
        }
    }

    private fun loadUid() {
        lifecycleScope.launch(Dispatchers.IO) {
            uid = (application as GreenApp).userPreferences.readUid()
        }
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