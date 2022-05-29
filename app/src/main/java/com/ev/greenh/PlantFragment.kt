package com.ev.greenh

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ev.greenh.databinding.FragmentPlantBinding
import com.ev.greenh.adapters.PlantAdapter
import com.ev.greenh.models.Plant
import com.ev.greenh.util.Resource
import com.ev.greenh.util.visible
import com.ev.greenh.viewmodels.PlantViewModel

class PlantFragment:Fragment(), PlantAdapter.OnItemClickListener {

    private var _binding: FragmentPlantBinding?=null
    private val binding get() = _binding!!
    private lateinit var plants:List<Plant>
    private lateinit var viewModel: PlantViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.plantsResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    binding.pbPlants.visible(true)
                }
                is Resource.Success -> {
                    if(it.data!=null) {
                        plants = it.data
                        setupRv(plants)
                    }
                    binding.pbPlants.visible(false)
                }
                is Resource.Error ->{
                    binding.pbPlants.visible(false)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.getAllPlants(getString(R.string.plant_sample_ref))

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun setupRv(list:List<Plant>){
        binding.pbPlants.visible(true)
        val plantAdapter = PlantAdapter(list,this)
        binding.rvPlants.apply {
            adapter = plantAdapter
            layoutManager = GridLayoutManager(context,2)
        }
        binding.pbPlants.visible(false)
    }

    override fun onItemClick(plantId:String) {
        val bundle = Bundle()
        bundle.putString("ID",plantId)
        val plantDetailFragment = PlantDetailFragment()
        plantDetailFragment.arguments = bundle
        (activity as MainActivity).setCurrentFragmentBack(plantDetailFragment)
    }
}