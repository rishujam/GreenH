package com.ev.greenh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ev.greenh.adapters.BagAdapter
import com.ev.greenh.databinding.FragmentBagBinding
import com.ev.greenh.models.Plant
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.PlantViewModel

class BagFragment: Fragment() {

    private var _binding: FragmentBagBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.email.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    viewModel.getBagItems(getString(R.string.cart),getString(R.string.plant_sample_ref),it.data!!)
                }
                is Resource.Loading ->{}
                is Resource.Error -> {}
            }
        })

        viewModel.bagItems.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    if(it.data!=null) setupRv(it.data)
                    binding.pbBag.visible(false)
                }
                is Resource.Error -> {
                    binding.pbBag.visible(false)
                    Toast.makeText(context, "Error Loading Items", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {}
            }
        })


    }

    private fun setupRv(map:Map<Plant,String>){
        var total = 0;
        for(i in map.keys){
            total+=i.price.toInt()
        }
        binding.tvTotal.text = "Total Price: ₹${total}"
        binding.noItemsBag.text = "No Of Items: ${map.size}"
        val bagAdapter = BagAdapter(map)
        binding.rvBagItems.apply {
            adapter = bagAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}