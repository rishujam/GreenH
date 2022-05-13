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
import com.ev.greenh.models.Plant
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.PlantViewModel
import com.razorpay.Checkout
import org.json.JSONObject

class BagBuyFragment:Fragment() {

    private var _binding:FragmentBagBuyBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:PlantViewModel
    private lateinit var amount: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagBuyBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        viewModel.getUserDetails(getString(R.string.user_ref),viewModel.email.value?.data!!)
        viewModel.getBagItems(getString(R.string.cart),getString(R.string.plant_sample_ref),viewModel.email.value?.data!!)

        Checkout.preload(context?.applicationContext)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profile.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    val data = it.data
                    Log.e("getUser:Bagbuy", data?.profileComplete.toString())
                    if(data!=null){
                        if(data.profileComplete){
                            binding.tvProfileName.text = data.name
                            binding.tvAddress.text = data.address.split("%")[0]+", "+data.address.split("%")[1]
                            binding.phoneBB.text = "Phone: ${data.phone}"
                        }else{
                           dialogOpen()
                        }
                    }
                }
                is Resource.Error -> {
                    Log.e("BagBuyFragment:getUserDetails()",it.message.toString())
                }
                is Resource.Loading -> {}
            }
        })

        viewModel.bagItems.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    val data = it.data
                    if(data!=null){
                        setupData(data)
                        binding.pbBagBuy.visible(false)
                        Log.e("BagBuyFragment", "BagItems Loaded Successfully")
                    }
                }
                is Resource.Error ->{
                    Toast.makeText(context, "Error loading Data", Toast.LENGTH_SHORT).show()
                    Log.e("BagBuyFragment",it.message.toString())
                }
                is Resource.Loading -> {}
            }
        })

        binding.btnContinue.setOnClickListener {
            startPayment(amount)
        }

        binding.btnChangeAddress.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("email",viewModel.email.value?.data)
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
            val editProfileFragment  = EditProfileFragment()
            val bundle = Bundle()
            bundle.putString("email",viewModel.email.value?.data!!)
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

    private fun startPayment(amount:String) {
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
            options.put("amount",amount.toInt()*100)

            val retryObj =  JSONObject()
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","rishuparashar7@gmail.com")
            prefill.put("contact","8076861086")
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
            Log.e("listenRazor: BagBuyFrag",(activity as MainActivity).successListener)
            (activity as MainActivity).supportFragmentManager.popBackStack()
            val ordersFragment = MyOrdersFragment()
            (activity as MainActivity).setCurrentFragment(ordersFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).successListener = ""
        _binding = null
    }
}