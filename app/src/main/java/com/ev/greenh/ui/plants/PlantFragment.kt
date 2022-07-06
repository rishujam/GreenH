package com.ev.greenh.ui.plants

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ev.greenh.*
import com.ev.greenh.databinding.FragmentPlantBinding
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.ui.profile.EditProfileFragment
import com.ev.greenh.util.Constants.VERSION
import com.ev.greenh.util.Resource
import com.ev.greenh.viewmodels.PlantViewModel

class PlantFragment:Fragment() {

    private var _binding: FragmentPlantBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlantViewModel
    private var currentFilter= "All"

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

//        binding.sendDtaa.setOnClickListener {
//            val source = FirestoreSource()
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    var no = 27
//                    while(no<50){
//                        source.addPlant(Plant(id = "s_$no", sunlight = "High", water = "1 time", status = "Available", category = "Indoor", featureNo = no, height = "20 cm", store = "Ganpati"))
//                        no++
//                    }
//                    withContext(Dispatchers.Main){
//                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
//                    }
//                }catch (e:Exception){
//                    withContext(Dispatchers.Main){
//                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }

        val allPlantsFragment = AllPlantsFragment()
        setCurrentFrag(allPlantsFragment)

        viewModel.getMinVersionToRun(getString(R.string.utils))

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
            if(currentFilter!="All"){
                setCurrentFrag(allPlantsFragment)
                setAllFilterColorDefault()
                binding.filterAll.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
                currentFilter = "All"
            }
        }
        binding.filterTable.setOnClickListener {
            if(currentFilter!="Table"){
                val tablePlantsFragment = TablePlantsFragment()
                setCurrentFrag(tablePlantsFragment)
                setAllFilterColorDefault()
                binding.filterTable.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
                currentFilter  = "Table"
            }
        }
        binding.filterIndoor.setOnClickListener {
            if(currentFilter!="Indoor"){
                val indoorPlantsFragment = IndoorPlantsFragment()
                setCurrentFrag(indoorPlantsFragment)
                setAllFilterColorDefault()
                binding.filterIndoor.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
                currentFilter = "Indoor"
            }
        }
        binding.filterOutdoor.setOnClickListener {
            if(currentFilter!="Outdoor"){
                val outdoorPlantsFragment = OutdoorPlantsFragment()
                setCurrentFrag(outdoorPlantsFragment)
                setAllFilterColorDefault()
                binding.filterOutdoor.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.theme_color
                ))
                currentFilter = "Outdoor"
            }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun setCurrentFrag(fragment: Fragment){
        childFragmentManager.beginTransaction().apply {
            replace(R.id.flPlants,fragment)
            commit()
        }
    }
    private fun setAllFilterColorDefault(){
        binding.filterAll.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.default_text_color
        ))
        binding.filterIndoor.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.default_text_color
        ))
        binding.filterOutdoor.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.default_text_color
        ))
        binding.filterTable.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.default_text_color
        ))
    }
}