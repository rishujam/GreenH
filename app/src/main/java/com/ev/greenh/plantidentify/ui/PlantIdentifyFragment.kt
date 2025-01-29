package com.ev.greenh.plantidentify.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ev.greenh.databinding.FragmentPlantScannerBinding
import com.ev.greenh.plantidentify.ui.composable.PlantScannerScreen
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.PermissionManager
import com.ev.greenh.util.PermissionType
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
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            PermissionManager.evaluateResult(result) { absResult ->
                if(absResult) {
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
        PermissionManager.launch(
            PermissionType.CAMERA,
            requireContext(),
            permissionLauncher
        ) {
            Toast.makeText(context, "Permission Already Granted", Toast.LENGTH_SHORT).show()
        }
        binding?.cvPlantScanner?.setContent {
            context?.let {
                PlantScannerScreen(viewModel, it.applicationContext)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}