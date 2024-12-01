package com.example.admin.ui.shop.detail

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.core.ui.IntentOpener
import com.core.ui.hide
import com.core.ui.show
import com.core.util.Constants
import com.core.util.Resource
import com.core.util.toByteArray
import com.example.admin.data.data_source.FirebaseDataSource
import com.example.admin.data.repo.AdminRepository
import com.example.admin.data.repo.AdminRepositoryFirebaseImpl
import com.example.admin.databinding.FragmentEditPlantBinding
import com.example.admin.ui.ViewModelFactory
import com.example.admin.ui.shop.ShopHomeViewModel
import com.example.admin.ui.shop.model.PlantAdmin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 28/11/24.
 */

class EditPlantFragment : Fragment() {

    private var _binding: FragmentEditPlantBinding? = null
    private lateinit var viewModel: EditPlantViewModel
    private val binding get() = _binding
    private var plantDetail: PlantAdmin? = null
    private var selectedImage: ByteArray? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            binding?.ivPlantImage?.setImageURI(uri)
            selectedImage = uri?.toByteArray(activity?.contentResolver)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            IntentOpener.openImagePicker(
                context,
                onLaunch = { intent ->
                    pickImageLauncher.launch(intent)
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditPlantBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fireStoreDataSource = FirebaseDataSource()
        val repo: AdminRepository = AdminRepositoryFirebaseImpl(fireStoreDataSource)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[EditPlantViewModel::class.java]
        val id = arguments?.getString(Constants.Args.PLANT_ID)
        id?.let {
            lifecycleScope.launch {
                viewModel.getPlantState.collectLatest { state ->
                    when(state) {
                        is Resource.Loading -> {
                            binding?.pbEditPlant?.show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_SHORT)
                                .show()
                            binding?.pbEditPlant?.hide()
                        }
                        is Resource.Success -> {
                            binding?.pbEditPlant?.hide()
                            state.data?.let { plant ->
                                plantDetail = plant
                                setupData(plant)
                            }
                        }
                    }
                }
            }
            viewModel.getPlantDetail(
                id
            )
        }
        lifecycleScope.launch {
            viewModel.postPlantState.collectLatest {
                when(it) {
                    is Resource.Success -> {
                        binding?.pbEditPlant?.hide()
                        Toast.makeText(context, "Plant Saved", Toast.LENGTH_SHORT).show()
                        activity?.onBackPressedDispatcher?.onBackPressed()
                    }

                    is Resource.Error -> {
                        binding?.pbEditPlant?.hide()
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }

                    is Resource.Loading -> {
                        binding?.pbEditPlant?.show()
                    }
                    else -> {}
                }
            }
        }
        binding?.apply {
            ivPlantImage.setOnClickListener {
                IntentOpener.openImagePicker(
                    context = context,
                    requestPermission = { permission ->
                        requestPermissionLauncher.launch(permission)
                    },
                    onLaunch = { intent ->
                        pickImageLauncher.launch(intent)
                    }
                )
            }

            btnSubmitEditPlant.setOnClickListener {
                val isAvailable = tbAvailableEditPlant.isChecked
                val price = etPriceEditPlant.text.toString()
                val name = etNameEditPlant.text.toString()
                val des = etNameEditPlant.text.toString()
                val height = etApxHeightEditPlant.text.toString()
                val sunlight =
                    rgSunlightEditPlant.findViewById<RadioButton>(rgSunlightEditPlant.checkedRadioButtonId).text.toString()
                val water =
                    rgWaterEditPlant.findViewById<RadioButton>(rgWaterEditPlant.checkedRadioButtonId).text.toString()
                val category =
                    rgCategoryEditPlant.findViewById<RadioButton>(rgCategoryEditPlant.checkedRadioButtonId).text.toString()
                val maintenance =
                    rgMaintenanceEditPlant.findViewById<RadioButton>(rgMaintenanceEditPlant.checkedRadioButtonId).text.toString()
                if (
                    price.isNotEmpty() &&
                    name.isNotEmpty() &&
                    des.isNotEmpty() &&
                    height.isNotEmpty() &&
                    (plantDetail?.imageUrl != null || selectedImage != null)
                ) {
                    val plant = PlantAdmin(
                        id = id,
                        name = name,
                        des = des,
                        sunlight = sunlight,
                        water = water,
                        availability = isAvailable,
                        category = category,
                        imageUrl = plantDetail?.imageUrl,
                        "",
                        price = price.toLongOrNull() ?: 0L,
                        approxHeight = height.toLongOrNull() ?: 0L,
                        maintenance = maintenance
                    )
                    id?.let {
                        viewModel.updatePlantDetail(
                            id = id,
                            plant = plant,
                            selectedImage
                        )
                    } ?: run {
                        viewModel.savePlantDetail(
                            selectedImage,
                            plant
                        )
                    }
                } else {
                    Toast.makeText(context, "Please input data properly", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupData(plant: PlantAdmin) {
        binding?.apply {
            Glide.with(root).load(plant.imageUrl).into(ivPlantImage)
            tbAvailableEditPlant.isChecked = plant.availability
            etNameEditPlant.setText(plant.name)
            etDesEditPlant.setText(plant.des)
            etApxHeightEditPlant.setText(plant.approxHeight.toString())
            etPriceEditPlant.setText(plant.price.toString())
            if(plant.maintenance == rbHigh.text.toString()) {
                rbHigh.isChecked = true
            } else if(plant.maintenance == rbLow.text.toString()) {
                rbLow.isChecked = true
            }
            if(plant.category == rbIndoor.text.toString()) {
                rbIndoor.isChecked = true
            } else if(plant.category == rbOutdoor.text.toString()) {
                rbOutdoor.isChecked = true
            }
            when(plant.sunlight) {
                rbDirect.text.toString() -> {
                    rbDirect.isChecked = true
                }
                rbInDirect.text.toString() -> {
                    rbInDirect.isChecked = true
                }
                rbNo.text.toString() -> {
                    rbNo.isChecked = true
                }
            }
            when(plant.water) {
                rbOnceADay.text.toString() -> {
                    rbOnceADay.isChecked = true
                }
                rbTwiceADay.text.toString() -> {
                    rbOnceADay.isChecked = true
                }
                rbOnceAWeek.text.toString() -> {
                    rbOnceAWeek.isChecked = true
                }
                rbTwiceAWeek.text.toString() -> {
                    rbTwiceAWeek.isChecked = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}