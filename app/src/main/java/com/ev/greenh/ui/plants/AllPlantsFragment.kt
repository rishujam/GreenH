package com.ev.greenh.ui.plants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ev.greenh.R
import com.ev.greenh.adapters.PlantAdapter
import com.ev.greenh.databinding.FragmentAllPlantsBinding
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Constants
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel

class AllPlantsFragment:Fragment() {

    private var _binding: FragmentAllPlantsBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlantViewModel
    private lateinit var plantAdapter: PlantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllPlantsBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()

        viewModel.getAllPlants(getString(R.string.plant_sample_ref))

        plantAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putString("ID",it.id)
            val plantDetailFragment = PlantDetailFragment()
            plantDetailFragment.arguments = bundle
            (activity as MainActivity).setCurrentFragmentBack(plantDetailFragment)
        }

        viewModel.plants.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    if(it.data!=null) {
                        val plants = it.data.plants
                        plantAdapter.differ.submitList(plants.toList())
                        val totalPages = plants.size/ Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.plantsPage == totalPages
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getAllPlants(getString(R.string.plant_sample_ref))
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun hideProgressBar() {
        binding.pbAllPlants.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.pbAllPlants.visibility = View.VISIBLE
        isLoading = true
    }

    private fun setupRv(){
        plantAdapter = PlantAdapter()
        binding.rvAllPlants.apply {
            adapter = plantAdapter
            layoutManager = GridLayoutManager(activity,2)
            addOnScrollListener(this@AllPlantsFragment.scrollListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
}