package com.ev.greenh.plantidentify.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ev.greenh.databinding.FragmentPlantScannerBinding
import com.ev.greenh.plantidentify.ui.composable.PlantScannerScreen
import com.ev.greenh.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

/*
 * Created by Sudhanshu Kumar on 22/10/23.
 */

@AndroidEntryPoint
class PlantIdentifyFragment : Fragment() {

    private var _binding: FragmentPlantScannerBinding? = null
    private val binding get() = _binding
    private val viewModel: PlantIdentifyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantScannerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.hideNav()
        if (!hasCameraPermission()) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.CAMERA), 0
            )
        }
        binding?.cvPlantScanner?.setContent {
            context?.let {
                PlantScannerScreen(viewModel, it.applicationContext)
            }
        }
    }

    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}