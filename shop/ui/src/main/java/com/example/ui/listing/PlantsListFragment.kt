package com.example.ui.listing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.core.ui.hide
import com.core.ui.show
import com.core.util.Resource
import com.example.ui.databinding.FragmentPlantListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

@AndroidEntryPoint
class PlantsListFragment : Fragment() {

    private var _binding: FragmentPlantListBinding?=null
    private val binding get() = _binding
    private val viewModel by viewModels<PlantListViewModel>()
    private var plantAdapter: PlantAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect {
                when(it) {
                    is Resource.Error -> {
                        binding?.pbPlantList?.hide()
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding?.pbPlantList?.show()
                    }
                    is Resource.Success -> {
                        binding?.pbPlantList?.hide()
                        setupRv()
                        plantAdapter?.differ?.submitList(it.data)
                    }
                }
            }
        }
        viewModel.getList()
    }

    private fun setupRv() {
        plantAdapter = PlantAdapter()
        val snapHelper = PlantListSpanHelper(context)
        binding?.rvAllPlants?.apply {
            adapter = plantAdapter
            layoutManager = PlantListLayoutManager(context)
            snapHelper.attachToRecyclerView(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}