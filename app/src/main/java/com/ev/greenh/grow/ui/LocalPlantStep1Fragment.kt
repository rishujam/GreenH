package com.ev.greenh.grow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ev.greenh.databinding.FragmentLocalPlantsStep1Binding
import com.ev.greenh.grow.ui.composable.LocalPlantStep1Screen

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

class LocalPlantStep1Fragment : Fragment() {

    private var _binding: FragmentLocalPlantsStep1Binding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocalPlantsStep1Binding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.cvLocalPlantStep1?.setContent {
            LocalPlantStep1Screen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}