package com.ev.greenh

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ev.greenh.databinding.FragmentBuyDirectBinding
import com.ev.greenh.models.Order
import com.ev.greenh.models.Plant
import com.ev.greenh.models.Profile
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.ui.order.MyOrdersFragment
import com.ev.greenh.ui.profile.EditProfileFragment
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.PlantViewModel
import com.google.android.material.snackbar.Snackbar
import com.razorpay.Checkout
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class DirectBuyFragment : Fragment() {

    private var _binding: FragmentBuyDirectBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlantViewModel
    private lateinit var plantInfo:Plant
    private lateinit var profile:Profile
    private lateinit var totalAmount:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyDirectBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).viewModel
        val plantId = arguments?.getString("plantIdBF").toString()
        viewModel.getSinglePlant(getString(R.string.plant_sample_ref), plantId)

        Checkout.preload(context?.applicationContext)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.readUid()

        viewModel.uid.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    val user = it.data
                    if(user!=null){
                        viewModel.getUserDetails(getString(R.string.user_ref), user)
                    }
                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    Log.e("DirectBuy:email", it.message.toString())
                }
                else ->{}
            }
        })
        viewModel.profile.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    val data = it.data
                    if (data != null) {
                        if (data.profileComplete) {
                            val address = data.address.split("%")
                            binding.tvAddress.text = address[0] + ", " + address[1]
                            binding.tvProfileName.text = data.name
                            binding.tvPhoneBD.text = "Phone: ${data.phone}"
                            profile = data
                        } else {
                            dialogOpen()
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, "Error Loading", Toast.LENGTH_SHORT).show()
                    Log.e("DirectBuyFragment", it.message.toString())
                }
                is Resource.Loading -> {}
            }
        })
        viewModel.plantResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    val plant = it.data
                    if (plant != null) {
                        binding.tvQuantityItemBuy.text = "Quantity: 1"
                        binding.tvPriceItemBuy.text = "₹${plant.price.toInt()}"
                        binding.tvNameItemBuy.text = "${plant.name}"
                        Glide.with(binding.root).load(plant.imageLocation).into(binding.ivBuyItem)
                        binding.progressBar2.visible(false)
                        var deliveryCharge = 29
                        if (plant.price.toInt() > 299) {
                            deliveryCharge = 0
                        }
                        totalAmount = (plant.price.toInt() + deliveryCharge).toString()
                        plantInfo = plant
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, "Error Loading Details", Toast.LENGTH_SHORT).show()
                    Log.e("BuyFragment", it.message.toString())
                }
                is Resource.Loading -> {}
            }
        })

        viewModel.placeOrder.observe(viewLifecycleOwner, Observer {
            when (it.getContentIfNotHandled()) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    (activity as MainActivity).paymentData = null
                    (activity as MainActivity).successListener = ""
                    binding.cdPb.visibility = View.GONE
                    Snackbar.make(binding.root, "Order Placed Successfully", Snackbar.LENGTH_SHORT)
                        .show()
                    val ordersFragment = MyOrdersFragment()
                    (activity as MainActivity).supportFragmentManager.popBackStack()
                    (activity as MainActivity).setCurrentFragment(ordersFragment)
                    (activity as MainActivity).setCurrentIcon()
                }
                is Resource.Error -> {
                    binding.progressBar2.visibility = View.INVISIBLE
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
                        .show()
                    Log.e("DirectBuy: placeOrder",it.peekContent().message.toString())
                }
                else -> {}
            }
        })
        viewModel.razorpayOrderId.observe(viewLifecycleOwner, Observer {
            when (it.getContentIfNotHandled()) {
                is Resource.Error -> {
                    binding.progressBar2.visibility = View.INVISIBLE
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
                        .show()
                    Log.e("DirectBuy: placeOrder",it.peekContent().message.toString())
                }
                is Resource.Success -> {
                    val data = it.peekContent().data
                    if (data != null) {
                        val sdf = SimpleDateFormat("dd/MM/yyyy,hh:mm:ss", Locale.ENGLISH)
                        val currentDate = sdf.format(Date())
                        val estDeliveryDate = currentDate.replaceRange(
                            0,
                            2,
                            "${currentDate.split("/")[0].toInt() + 2}"
                        )
                        when (binding.rgPayMethod.checkedRadioButtonId) {
                            R.id.payCod -> viewModel.placeOrder(
                                Order(
                                    data.id,
                                    profile.uid,
                                    mutableListOf("${plantInfo.id},1"),
                                    currentDate,
                                    (totalAmount.toInt()-plantInfo.price.toInt()).toString(),
                                    totalAmount,
                                    "Order Placed",
                                    estDeliveryDate,
                                    "",
                                    profile.address,
                                    profile.phone
                                ), getString(R.string.orders)
                            )
                            R.id.payOnline -> startPayment(totalAmount, data.id)
                        }
                    }
                }
                is Resource.Loading -> {}
                else -> {}
            }
        })

        binding.btnContinue.setOnClickListener {
            binding.cdPb.visibility = View.VISIBLE
            viewModel.generateOrderId(hashMapOf("amount" to totalAmount.toInt()))
        }
        binding.btnChangeAddress.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("email", profile.emailId)
            val editProfileFragment = EditProfileFragment()
            editProfileFragment.arguments = bundle
            (activity as MainActivity).setCurrentFragmentBack(editProfileFragment)
        }
        binding.payCod.setOnClickListener {
            binding.btnContinue.text = "Place Order"
        }
        binding.payOnline.setOnClickListener {
            binding.btnContinue.text = "Continue to payment"
        }
    }

    private fun dialogOpen() {
        val errorDialog = Dialog(requireContext())
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        errorDialog.setContentView(R.layout.dialog_error)
        val btnCompleteProfile: Button = errorDialog.findViewById(R.id.btnCompleteProfile)
        val ibCloseDialog: ImageButton = errorDialog.findViewById(R.id.ibCloseDialog)
        errorDialog.show()
        errorDialog.setCancelable(false)
        errorDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnCompleteProfile.setOnClickListener {
            val editProfileFragment = EditProfileFragment()
            val bundle = Bundle()
            bundle.putString("email", profile.emailId)
            editProfileFragment.arguments = bundle
            (activity as MainActivity).setCurrentFragmentBack(editProfileFragment)
        }

        ibCloseDialog.setOnClickListener {
            errorDialog.dismiss()
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }

    private fun startPayment(amount: String, orderId: String) {
//        val activity: Activity = (activity as MainActivity)
//        val co = Checkout()
//        co.setKeyID(getString(R.string.razor_pay))
//        try {
//            val options = JSONObject()
//            options.put("name", "Razorpay Corp")
//            options.put("description", "Demoing Charges")
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
//            options.put("theme.color", "#3399cc")
//            options.put("currency", "INR")
//            options.put("order_id", orderId);
//            options.put("amount", amount.toInt() * 100)
//
//            val retryObj = JSONObject()
//            retryObj.put("enabled", true)
//            retryObj.put("max_count", 4)
//            options.put("retry", retryObj)
//
//            val prefill = JSONObject()
//            prefill.put("email", profile.emailId)
//            prefill.put("contact", profile.phone)
//            options.put("prefill", prefill)
//            co.open(activity, options)
//        } catch (e: Exception) {
//            Toast.makeText(activity, "Error in payment", Toast.LENGTH_LONG).show()
//            Log.e("DirectBuyFrag", e.message.toString())
//            e.printStackTrace()
//        }
    }

    override fun onResume() {
        super.onResume()
        if ((activity as MainActivity).successListener == "Y") {
            Log.e("listenRazor: DirectBuy", (activity as MainActivity).successListener)
            binding.cdPb.visibility = View.VISIBLE
            val sdf = SimpleDateFormat("dd/MM/yyyy,hh:mm:ss", Locale.ENGLISH)
            val currentDate = sdf.format(Date())
            val estDeliveryDate =
                currentDate.replaceRange(0, 2, "${currentDate.split("/")[0].toInt() + 2}")
            val paymentData = (activity as MainActivity).paymentData
            if (paymentData != null) {
                val deliveryCharge = totalAmount.toInt()-plantInfo.price.toInt()
                viewModel.placeOrder(
                    Order(
                        paymentData.orderId,
                        profile.uid,
                        mutableListOf("${plantInfo.id},1"),
                        currentDate,
                        deliveryCharge.toString(),
                        totalAmount,
                        "Order Placed",
                        estDeliveryDate,
                        paymentData.paymentId,
                        profile.address,
                        profile.phone
                    ), getString(R.string.orders)
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.e("DirectBuyFrag", "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("DirectBuyFrag", "onDestroy()")
        _binding = null
    }
}