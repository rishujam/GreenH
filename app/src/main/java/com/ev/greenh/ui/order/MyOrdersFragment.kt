package com.ev.greenh.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ev.greenh.R
import com.ev.greenh.adapters.MyOrderAdapter
import com.ev.greenh.databinding.FragmentMyordersBinding
import com.ev.greenh.models.uimodels.MyOrder
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel
import com.google.android.material.snackbar.Snackbar

class MyOrdersFragment:Fragment(), MyOrderAdapter.OrderDetails {

    private var _binding:FragmentMyordersBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyordersBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.readUid()
        viewModel.uid.observe(viewLifecycleOwner, Observer {
            when(it.getContentIfNotHandled()){
                is Resource.Success ->{
                    val user = it.peekContent().data
                    if(user!=null){
                        viewModel.getUserOrders(user,getString(R.string.orders),getString(R.string.plant_sample_ref))
                    }
                }
                is Resource.Loading ->{}
                is Resource.Error ->{
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    Log.e("MyOrderFrag:email",it.peekContent().message.toString())
                }
                else ->{}
            }
        })



        viewModel.getUserOrders.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Error ->{
                    binding.progressBar3.visibility = View.INVISIBLE
                    Snackbar.make(binding.root,"Something went wrong",Snackbar.LENGTH_SHORT).show()
                    Log.e("MyOrderFragment:getOrders", it.message.toString())
                }
                is Resource.Loading ->{}
                is Resource.Success ->{
                    if(it.data!=null && it.data.isNotEmpty()){
                        setupData(it.data)
                    }else{
                        binding.emptyRvText.visibility = View.VISIBLE
                    }
                    binding.progressBar3.visibility = View.INVISIBLE
                }
            }
        })

    }

    private fun setupData(list:List<MyOrder>){
        val myOrderAdapter = MyOrderAdapter(list,this)
        binding.rvMyOrders.apply {
            adapter = myOrderAdapter
            layoutManager =LinearLayoutManager(context)
        }
    }

    override fun onOrderClick(orderId: String) {
        val bundle = Bundle()
        bundle.putString("orderId",orderId)
        val orderDetailsFragment = OrderDetailsFragment()
        orderDetailsFragment.arguments = bundle
        (activity as MainActivity).setCurrentFragmentBack(orderDetailsFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }
}