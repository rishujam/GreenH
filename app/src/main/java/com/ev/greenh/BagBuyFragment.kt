package com.ev.greenh

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ev.greenh.adapters.BagBuyAdapter
import com.ev.greenh.databinding.FragmentBagBuyBinding
import com.ev.greenh.models.Order
import com.ev.greenh.models.Plant
import com.ev.greenh.models.Profile
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.PlantViewModel
import com.google.android.material.snackbar.Snackbar
import com.razorpay.Checkout
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class BagBuyFragment:Fragment() {

    private var _binding:FragmentBagBuyBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:PlantViewModel
    private lateinit var amount: String
    private val plantIds = mutableListOf<String>()
    private lateinit var profile:Profile
    private lateinit var user:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagBuyBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        Checkout.preload(context?.applicationContext)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.readUid()
        viewModel.uid.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()) {
                is Resource.Success ->{
                    val uid = it.peekContent().data
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
                            binding.tvProfileName.text = data.name
                            binding.tvAddress.text = data.address.split("%")[0]+", "+data.address.split("%")[1]
                            binding.phoneBB.text = "Phone: ${data.phone}"
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

        viewModel.bagItems.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Success -> {
                    val data = it.peekContent().data
                    if(data!=null){
                        setupData(data)
                        for(i in data){
                            plantIds.add("${i.key.id},${i.value.split(",")[0]}")
                        }
                        Log.e("BagBuyFrag1", plantIds.size.toString())
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
                        val estDeliveryDate = currentDate.replaceRange(
                            0,
                            2,
                            "${currentDate.split("/")[0].toInt() + 2}"
                        )
                        var deliveryCharge = if(amount.toInt()>299) "0" else "29"
                        Log.e("BagBuyFrag2", plantIds.size.toString())
                        when (binding.rgPayMethodBB.checkedRadioButtonId) {
                            R.id.payCodBB -> viewModel.placeOrder(
                                Order(
                                    data.id,
                                    profile.uid,
                                    plantIds,
                                    currentDate,
                                    deliveryCharge,
                                    (deliveryCharge.toInt()+amount.toInt()).toString(),
                                    "Order Placed",
                                    estDeliveryDate,
                                    "",
                                    profile.address,
                                    profile.phone
                                ), getString(R.string.orders)
                            )
                            R.id.payOnlineBB -> startPayment((deliveryCharge.toInt()+amount.toInt()).toString(), data.id)
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
                    binding.cdPb.visibility = View.GONE
                    Snackbar.make(binding.root, "Order Placed Successfully", Snackbar.LENGTH_SHORT)
                        .show()
                    val ordersFragment = MyOrdersFragment()
                    (activity as MainActivity).supportFragmentManager.popBackStack()
                    (activity as MainActivity).setCurrentFragment(ordersFragment)
                    (activity as MainActivity).setCurrentIcon()
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
            binding.cdPb.visibility = View.VISIBLE
            viewModel.generateOrderId(hashMapOf("amount" to amount.toInt()))
        }

        binding.payCodBB.setOnClickListener {
            binding.btnContinue.text = "Place Order"
        }
        binding.payOnlineBB.setOnClickListener {
            binding.btnContinue.text = "Continue to payment"
        }

        binding.btnChangeAddress.setOnClickListener {
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
        errorDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnCompleteProfile.setOnClickListener {
            errorDialog.dismiss()
            val editProfileFragment  = EditProfileFragment()
            val bundle = Bundle()
            bundle.putString("email",user)
            editProfileFragment.arguments  = bundle
            (activity as MainActivity).setCurrentFragmentBack(editProfileFragment)
        }

        ibCloseDialog.setOnClickListener {
            errorDialog.dismiss()
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }

    private fun setupData(map:Map<Plant,String>){
        var total = 0
        for(i in map.values.toList()){
            total +=i.split(",")[1].toInt()
        }
        binding.totalBB.text = "Total Amount to be paid: ₹$total"
        val bagBuyAdapter = BagBuyAdapter(map)
        binding.rvBagBuy.apply{
            adapter = bagBuyAdapter
            layoutManager = LinearLayoutManager(context)
        }
        amount = total.toString()
    }

    private fun startPayment(amount:String, orderId:String) {
        val activity: Activity = (activity as MainActivity)
        val co = Checkout()
        co.setKeyID(getString(R.string.razor_pay))
        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc")
            options.put("currency","INR")
            options.put("order_id", orderId);
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
        if((activity as MainActivity).successListener=="Y"){
            binding.cdPb.visibility = View.VISIBLE
            val sdf = SimpleDateFormat("dd/MM/yyyy,hh:mm:ss", Locale.ENGLISH)
            val currentDate = sdf.format(Date())
            val estDeliveryDate =
                currentDate.replaceRange(0, 2, "${currentDate.split("/")[0].toInt() + 2}")
            val paymentData = (activity as MainActivity).paymentData
            if (paymentData != null) {
                val deliveryCharge = if(amount.toInt()>299) "0" else "29"
                val totalAmount = (deliveryCharge.toInt()+amount.toInt()).toString()
                viewModel.placeOrder(
                    Order(
                        paymentData.orderId,
                        profile.uid,
                        plantIds,
                        currentDate,
                        deliveryCharge,
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}