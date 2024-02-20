package com.ev.greenh.grow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ev.greenh.databinding.FragmentLocalPlantListBinding
import com.ev.greenh.grow.ui.composable.LocalPlantListScreen
import dagger.hilt.android.AndroidEntryPoint

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@AndroidEntryPoint
class LocalPlantListFragment : Fragment() {

    private var _binding: FragmentLocalPlantListBinding? = null
    private val binding get() = _binding
    private val viewModel: LocalPlantListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocalPlantListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.cvLocalPlantList?.setContent {
            LocalPlantListScreen(viewModel)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}