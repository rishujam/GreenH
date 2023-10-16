package com.ev.greenh.grow.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.Fragment
import com.ev.greenh.databinding.FragmentGrowBinding
import com.ev.greenh.grow.ui.composable.GrowScreen
import com.ev.greenh.grow.ui.model.GrowListingItem
import com.ev.greenh.ui.MainActivity

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

class GrowFragment : Fragment() {

    private var _binding: FragmentGrowBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGrowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = mutableListOf<GrowListingItem>()
        data.add(GrowListingItem("Rose"))
        data.add(GrowListingItem("Lily"))
        data.add(GrowListingItem("Tulsi"))
        binding?.cvGrowFrag?.setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                GrowScreen(data) {
                    itemClick(it)
                }
            }
        }
    }

    private fun itemClick(id: String) {
        val fragment = GrowDetailFragment()
        (activity as MainActivity).setCurrentFragmentBack(fragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}