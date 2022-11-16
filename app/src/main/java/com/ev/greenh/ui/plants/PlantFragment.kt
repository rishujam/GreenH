package com.ev.greenh.ui.plants

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
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
import com.ev.greenh.adapters.PlantPagingAdapter
import com.ev.greenh.databinding.FragmentPlantBinding
import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.models.Plant
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
    private lateinit var plantAdapter: PlantPagingAdapter
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
                when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        binding.filterAll.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.theme_color_dark))}
                    Configuration.UI_MODE_NIGHT_NO -> {
                        binding.filterAll.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.dark_green))
                    }
                }
            }
            "Indoor" ->{
                setupIndoorRv()
                viewModel.getIndoorPlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        binding.filterIndoor.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.theme_color_dark))}
                    Configuration.UI_MODE_NIGHT_NO -> {
                        binding.filterIndoor.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.dark_green))
                    }
                }
            }
            "Table" ->{
                setupTableRv()
                viewModel.getTablePlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        binding.filterTable.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.theme_color_dark))}
                    Configuration.UI_MODE_NIGHT_NO -> {
                        binding.filterTable.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.dark_green))
                    }
                }
            }
        }

        viewModel.allPlantsPaginated.observe(viewLifecycleOwner, Observer {
            plantAdapter.submitData(lifecycle, it)
        })



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
                when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        binding.filterAll.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.theme_color_dark))}
                    Configuration.UI_MODE_NIGHT_NO -> {
                        binding.filterAll.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.dark_green))
                    }
                }
                viewModel.currentFilter = "All"
            }
        }
        binding.filterTable.setOnClickListener {
            if(viewModel.currentFilter!="Table"){
                setupTableRv()
                viewModel.getTablePlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        binding.filterTable.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.theme_color_dark))}
                    Configuration.UI_MODE_NIGHT_NO -> {
                        binding.filterTable.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.dark_green))
                    }
                }
                viewModel.currentFilter  = "Table"
            }
        }
        binding.filterIndoor.setOnClickListener {
            if(viewModel.currentFilter!="Indoor"){
                setupIndoorRv()
                viewModel.getIndoorPlants(getString(R.string.plant_sample_ref))
                setAllFilterColorDefault()
                when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {binding.filterIndoor.setTextColor(ContextCompat.getColor(requireContext(),
                        R.color.theme_color_dark))}
                    Configuration.UI_MODE_NIGHT_NO -> {
                        binding.filterIndoor.setTextColor(ContextCompat.getColor(requireContext(),
                            R.color.dark_green))
                    }
                }
                viewModel.currentFilter = "Indoor"
            }
        }
    }

    private fun hideProgressBar() {
        binding.pbAllPlants.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.pbAllPlants.visibility = View.VISIBLE
    }

    private fun setupRv(){
        plantAdapter = PlantPagingAdapter()
        binding.rvAllPlants.apply {
            adapter = plantAdapter
            layoutManager = GridLayoutManager(activity,2)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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