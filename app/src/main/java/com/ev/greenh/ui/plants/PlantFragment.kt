package com.ev.greenh.ui.plants

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ev.greenh.*
import com.ev.greenh.adapters.PlantAdapter
import com.ev.greenh.databinding.FragmentPlantBinding
import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Constants
import com.ev.greenh.util.Constants.VERSION
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlantFragment:Fragment() {

    private var _binding: FragmentPlantBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlantViewModel
    private lateinit var plantAdapter: PlantAdapter
    private lateinit var indoorPlantAdapter:PlantAdapter
    private lateinit var tablePlantAdapter:PlantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).viewModel
        (activity as MainActivity).viewNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMinVersionToRun(getString(R.string.utils))


        when(viewModel.currentFilter){
            "All" ->{
                setupRv()
                viewModel.getAllPlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                binding.filterAll.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
            }
            "Indoor" ->{
                setupIndoorRv()
                viewModel.getIndoorPlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                binding.filterIndoor.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
            }
            "Table" ->{
                setupTableRv()
                viewModel.getTablePlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                binding.filterTable.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
            }
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
                    Log.e("PlantsError", it.message.toString())
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.plantsIndoor.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    if(it.data!=null) {
                        val plants = it.data.plants
                        indoorPlantAdapter.differ.submitList(plants.toList())
                        val totalPages = plants.size/ Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.plantsIndoorPage == totalPages
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.plantsTable.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    if(it.data!=null) {
                        val plants = it.data.plants
                        tablePlantAdapter.differ.submitList(plants.toList())
                        val totalPages = plants.size/ Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.plantsTablePage == totalPages
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

//        binding.sendDtaa.setOnClickListener {
//            binding.pbAllPlants.visibility = View.VISIBLE
//            val source = FirestoreSource()
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    var no = 50
//                    while(no<94){
//                        source.addUrl("s_$no")
//                        no++
//                    }
//                    withContext(Dispatchers.Main){
//                        binding.pbAllPlants.visibility = View.INVISIBLE
//                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
//                    }
//                }catch (e:Exception){
//                    withContext(Dispatchers.Main){
//                        binding.pbAllPlants.visibility = View.INVISIBLE
//                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }

        viewModel.minVersion.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    if(it.data!=null){
                        if(it.data>VERSION){
                            updateDialog()
                        }
                    }
                }
                is Resource.Error ->{}
                is Resource.Loading ->{}
            }
        })

        binding.filterAll.setOnClickListener {
            if(viewModel.currentFilter!="All"){
                setupRv()
                viewModel.getAllPlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                binding.filterAll.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
                viewModel.currentFilter = "All"
            }
        }
        binding.filterTable.setOnClickListener {
            if(viewModel.currentFilter!="Table"){
                setupTableRv()
                viewModel.getTablePlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                binding.filterTable.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
                viewModel.currentFilter  = "Table"
            }
        }
        binding.filterIndoor.setOnClickListener {
            if(viewModel.currentFilter!="Indoor"){
                setupIndoorRv()
                viewModel.getIndoorPlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                binding.filterIndoor.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
                viewModel.currentFilter = "Indoor"
            }
        }
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
            Log.e("scrolled ${viewModel.currentFilter}", "$isScrolling")
            if (shouldPaginate) {
                if(viewModel.currentFilter=="All"){
                    viewModel.getAllPlants(getString(R.string.plant_sample_ref))
                }else if (viewModel.currentFilter == "Indoor"){
                    viewModel.getIndoorPlants(getString(R.string.plant_sample_ref))
                } else if( viewModel.currentFilter == "Table") {
                    viewModel.getTablePlants(getString(R.string.plant_sample_ref))
                }
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
            addOnScrollListener(this@PlantFragment.scrollListener)
        }

        plantAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putString("ID",it.id)
            val plantDetailFragment = PlantDetailFragment()
            plantDetailFragment.arguments = bundle
            (activity as MainActivity).setCurrentFragmentBack(plantDetailFragment)
        }
    }

    private fun setupIndoorRv(){
        indoorPlantAdapter = PlantAdapter()
        binding.rvAllPlants.apply {
            adapter = indoorPlantAdapter
            layoutManager = GridLayoutManager(activity,2)
            addOnScrollListener(this@PlantFragment.scrollListener)
        }

        indoorPlantAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putString("ID",it.id)
            val plantDetailFragment = PlantDetailFragment()
            plantDetailFragment.arguments = bundle
            (activity as MainActivity).setCurrentFragmentBack(plantDetailFragment)
        }
    }

    private fun setupTableRv(){
        tablePlantAdapter = PlantAdapter()
        binding.rvAllPlants.apply {
            adapter = tablePlantAdapter
            layoutManager = GridLayoutManager(activity,2)
            addOnScrollListener(this@PlantFragment.scrollListener)
        }

        tablePlantAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putString("ID",it.id)
            val plantDetailFragment = PlantDetailFragment()
            plantDetailFragment.arguments = bundle
            (activity as MainActivity).setCurrentFragmentBack(plantDetailFragment)
        }
    }


    private fun updateDialog(){
        val errorDialog = Dialog(requireContext())
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        errorDialog.setContentView(R.layout.dialog_error)
        val btnUpdate: Button = errorDialog.findViewById(R.id.btnCompleteProfile)
        val ibCloseDialog: ImageButton = errorDialog.findViewById(R.id.ibCloseDialog)
        val dialogTitle: TextView = errorDialog.findViewById(R.id.textView6)
        val dialogBrief: TextView = errorDialog.findViewById(R.id.tvMessage)
        dialogBrief.text = "Update app to continue using"
        dialogTitle.text = "Update app"
        btnUpdate.text = "Update"
        ibCloseDialog.visibility = View.GONE
        errorDialog.show()
        errorDialog.setCancelable(false)
        errorDialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btnUpdate.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${activity?.packageName}")))
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${activity?.packageName}")))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.e("Life","PlantF: onPause" )
    }
    override fun onStop() {
        super.onStop()
        Log.e("Life","PlantF: onStop")
    }

    override fun onResume() {
        super.onResume()
        Log.e("Life", "PlantF: onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.e("Life", "PlantF: onStart")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("Life", "PlantF: onDestroyView")
        _binding=null
    }

    private fun setAllFilterColorDefault(){
        binding.filterAll.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.default_text_color
        ))
        binding.filterIndoor.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.default_text_color
        ))
        binding.filterTable.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.default_text_color
        ))
    }
}