package com.ev.greenh.ui.order

import android.app.Activity
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
import com.ev.greenh.R
import com.ev.greenh.databinding.FragmentBagBuyyBinding
import com.ev.greenh.models.Order
import com.ev.greenh.models.Profile
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.ui.profile.EditProfileFragment
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel
import com.google.android.material.snackbar.Snackbar
import com.razorpay.Checkout
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class BagBuyFragment:Fragment() {

    private var _binding: FragmentBagBuyyBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:PlantViewModel
    private lateinit var subTotalGlobal: String
    private lateinit var total:String
    private val plantIds = mutableListOf<String>()
    private lateinit var profile:Profile
    private lateinit var user:String
    private lateinit var apiKey:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagBuyyBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        Checkout.preload(context?.applicationContext)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getApiKey(getString(R.string.utils))

        viewModel.uid.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success ->{
                    val uid = it.data
                    if(uid!=null){
                        user = uid
                        viewModel.getProfileSingleTime(getString(R.string.user_ref),uid)
                        viewModel.getBagItems(getString(R.string.cart),getString(R.string.plant_sample_ref),uid)
                    }else{
                        Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error ->{
                    Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading ->{}
                else -> {}
            }
        })
        viewModel.singleTimeProfile.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Success -> {
                    val data = it.peekContent().data
                    if(data!=null){
                        if(data.profileComplete){
                            binding.tvAddress.text = data.address.split("%")[0]+"\n"+data.address.split("%")[1]
                            binding.tvPhoneBB.text = data.phone
                            profile = data
                        }else{
                            dialogOpen()
                        }
                    }
                }
                is Resource.Error -> {
                    Log.e("BagBuyFragment:getUserDetails()",it.peekContent().message.toString())
                }
                is Resource.Loading -> {}
                else -> {}
            }
        })

        viewModel.apiKey.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Success ->{
                    apiKey = it.peekContent().data.toString()
                }
                is Resource.Error -> {}
                is Resource.Loading ->{}
                else -> {}
            }
        })

        viewModel.bagItems.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Success -> {
                    val data = it.peekContent().data
                    if(data!=null){
                        var subTotal = 0
                        for(i in data){
                            subTotal+=i.value.split(",")[1].toInt()
                            plantIds.add("${i.key.id},${i.value.split(",")[0]}")
                        }
                        subTotalGlobal = subTotal.toString()
                        val deliveryCharge = if(subTotalGlobal.toInt()>500) "0" else "59"
                        total = (deliveryCharge.toInt()+subTotalGlobal.toInt()).toString()
                        binding.tvSubtotal.text = "₹${subTotal}"
                        binding.tvDeliveryCharges.text = "₹${deliveryCharge}"
                        binding.tvTotal.text = "₹$total"
                        Log.e("BagBuyFragment", "BagItems Loaded Successfully")
                    }
                    binding.cdPb.visibility = View.GONE
                }
                is Resource.Error ->{
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                    Log.e("BagBuyFragment",it.peekContent().message.toString())
                }
                is Resource.Loading -> {}
                else -> {}
            }
        })

        viewModel.razorpayOrderId.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Loading ->{}
                is Resource.Error -> {
                    binding.cdPb.visibility = View.GONE
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
                        .show()
                    Log.e("BagBuyFrag: razorIdGet",it.peekContent().message.toString())
                }
                is Resource.Success ->{
                    val data = it.peekContent().data
                    if (data != null) {
                        val sdf = SimpleDateFormat("dd/MM/yyyy,hh:mm:ss", Locale.ENGLISH)
                        val currentDate = sdf.format(Date())
                        val nextDay = currentDate.split("/")[0].toInt() +2
                        var replacement = "$nextDay"
                        if(nextDay>30){
                            replacement ="02"
                        }
                        val estDeliveryDate = currentDate.replaceRange(
                            0,
                            2,
                            replacement
                        )
                        Log.e("BagBuyFrag2", plantIds.size.toString())
                        when (binding.rgPayMethodBB.checkedRadioButtonId) {
                            R.id.payCodBB -> viewModel.placeOrder(
                                Order(
                                    data.id,
                                    profile.uid,
                                    plantIds,
                                    currentDate,
                                    (total.toInt()-subTotalGlobal.toInt()).toString(),
                                    total,
                                    "Order Placed",
                                    estDeliveryDate,
                                    "",
                                    profile.address,
                                    profile.phone
                                ), getString(R.string.orders)
                            )
                            R.id.payOnlineBB -> startPayment(total, data.id)
                        }
                    }
                }
                else ->{}
            }
        })
        viewModel.placeOrder.observe(viewLifecycleOwner, Observer {
            when (it.getContentIfNotHandled()) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    (activity as MainActivity).paymentData = null
                    (activity as MainActivity).successListener = ""
                    viewModel.emptyUserCart(user,getString(R.string.cart))
                    binding.cdPb.visibility = View.GONE
                    Snackbar.make(binding.root, "Order Placed Successfully", Snackbar.LENGTH_SHORT)
                        .show()
                    val ordersFragment = MyOrdersFragment()
                    (activity as MainActivity).supportFragmentManager.popBackStack()
                    (activity as MainActivity).setCurrentFragment(ordersFragment)
//                    (activity as MainActivity).setCurrentIcon()
                }
                is Resource.Error -> {
                    binding.cdPb.visibility = View.GONE
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT)
                        .show()
                    Log.e("DirectBuy: placeOrder",it.peekContent().message.toString())
                }
                else -> {}
            }
        })

        binding.btnContinue.setOnClickListener {
            if(binding.payCodBB.isChecked || binding.payOnlineBB.isChecked){
                binding.cdPb.visibility = View.VISIBLE
                viewModel.generateOrderId(hashMapOf("amount" to total.toInt()))
            }else{
                Toast.makeText(context, "Select Payment Method", Toast.LENGTH_SHORT).show()
            }
        }

        binding.payCodBB.setOnClickListener {
            binding.btnContinue.text = "Place Order"
        }
        binding.payOnlineBB.setOnClickListener {
            binding.btnContinue.text = "Continue to payment"
        }

        binding.backBtn.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }

        binding.edit.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("email",user)
            val editProfileFragment = EditProfileFragment()
            editProfileFragment.arguments = bundle
            (activity as MainActivity).setCurrentFragmentBack(editProfileFragment)
        }
    }

    private fun dialogOpen(){
        val errorDialog = Dialog(requireContext())
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        errorDialog.setContentView(R.layout.dialog_error)
        val btnCompleteProfile: Button = errorDialog.findViewById(R.id.btnCompleteProfile)
        val ibCloseDialog: ImageButton = errorDialog.findViewById(R.id.ibCloseDialog)
        errorDialog.show()
        errorDialog.setCancelable(false)
        errorDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnCompleteProfile.setOnClickListener {
            errorDialog.dismiss()
            val editProfileFragment  = EditProfileFragment()
            val bundle = Bundle()
            bundle.putString("email",user)
            editProfileFragment.arguments  = bundle
            (activity as MainActivity).supportFragmentManager.popBackStack()
            (activity as MainActivity).setCurrentFragmentBack(editProfileFragment)
        }

        ibCloseDialog.setOnClickListener {
            errorDialog.dismiss()
            (activity as MainActivity).supportFragmentManager.popBackStack()

        }
    }

    private fun startPayment(amount:String, orderId:String) {
        val activity: Activity = (activity as MainActivity)
        val co = Checkout()
        co.setKeyID(apiKey)
        try {
            val options = JSONObject()
            options.put("name","Gardners Hub")
            options.put("description","Pay for plants")
            options.put("image",R.drawable.app_icon)
            options.put("theme.color", Color.GREEN)
            options.put("currency","INR")
            options.put("order_id", orderId)
            options.put("amount",amount.toInt()*100)

            val retryObj =  JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email", profile.emailId)
            prefill.put("contact",profile.phone)
            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.cdPb.visibility = View.GONE
        if((activity as MainActivity).successListener=="Y"){
            binding.cdPb.visibility = View.VISIBLE
            val sdf = SimpleDateFormat("dd/MM/yyyy,hh:mm:ss", Locale.ENGLISH)
            val currentDate = sdf.format(Date())
            val nextDay = currentDate.split("/")[0].toInt() +2
            var replacement = "$nextDay"
            if(nextDay>30){
                replacement ="02"
            }
            val estDeliveryDate = currentDate.replaceRange(
                0,
                2,
                replacement
            )
            val paymentData = (activity as MainActivity).paymentData
            if (paymentData != null) {
                viewModel.placeOrder(
                    Order(
                        paymentData.orderId,
                        profile.uid,
                        plantIds,
                        currentDate,
                        (total.toInt()-subTotalGlobal.toInt()).toString(),
                        total,
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.e("BagBuy", "onDestroyView")
    }
}