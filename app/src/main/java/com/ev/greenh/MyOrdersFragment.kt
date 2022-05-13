package com.ev.greenh

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ev.greenh.databinding.FragmentMyordersBinding
import com.ev.greenh.models.Order
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel

class MyOrdersFragment:Fragment() {

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

    }

    private fun setupData(list:List<Order>){

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }
}