package com.ev.greenh.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ev.greenh.databinding.FragmentHomeBinding
import com.ev.greenh.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTodayTip()
        binding?.cvHomeFrag?.setContent {
            HomeScreen(
                (activity as? MainActivity)?.activityViewModel,
                viewModel
            )
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.viewNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}