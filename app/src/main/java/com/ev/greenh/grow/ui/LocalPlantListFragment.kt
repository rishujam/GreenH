package com.ev.greenh.grow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ev.greenh.databinding.FragmentLocalPlantListBinding
import com.ev.greenh.grow.ui.composable.LocalPlantListScreen
import com.ev.greenh.grow.ui.composable.LocalPlantStep1Screen
import com.ev.greenh.ui.MainActivity

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

class LocalPlantListFragment : Fragment() {

    private var _binding: FragmentLocalPlantListBinding? = null
    private val binding get() = _binding

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
        (activity as? MainActivity)?.hideNav()
        binding?.cvLocalPlantList?.setContent {
            LocalPlantListScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}