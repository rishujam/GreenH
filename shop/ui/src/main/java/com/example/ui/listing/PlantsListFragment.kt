package com.example.ui.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.core.ui.hide
import com.core.ui.show
import com.core.util.Resource
import com.example.ui.databinding.FragmentPlantListBinding
import com.example.ui.detail.PlantDetailArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

@AndroidEntryPoint
class PlantsListFragment : Fragment() {

    private var _binding: FragmentPlantListBinding? = null
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
        setupRv()
        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding?.pbPlantList?.hide()
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Loading -> {
                        binding?.pbPlantList?.show()
                    }

                    is Resource.Success -> {
                        binding?.pbPlantList?.hide()
                        plantAdapter?.differ?.submitList(it.data)
                    }

                    null -> {}
                }
            }
        }
        viewModel.getList()
    }

    private fun setupRv() {
        plantAdapter = PlantAdapter()
        binding?.apply {
            rvAllPlants.adapter = plantAdapter
            rvAllPlants.layoutManager = LinearLayoutManager(context)
            rvAllPlants.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = (rvAllPlants.layoutManager as LinearLayoutManager).childCount
                    val totalItemCount = (rvAllPlants.layoutManager as LinearLayoutManager).getItemCount()
                    val firstVisibleItemPosition =
                        (rvAllPlants.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (
                        visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                        firstVisibleItemPosition >= 0
                    ) {
                        viewModel.getList()
                    }
                }
            })
            plantAdapter?.setOnItemClickListener { plant, _ ->
                val args = PlantDetailArgs(
                    id = plant.id,
                    plant = plant
                )
                val action = PlantsListFragmentDirections.navigateToDetail(args)
                Navigation.findNavController(root).navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}