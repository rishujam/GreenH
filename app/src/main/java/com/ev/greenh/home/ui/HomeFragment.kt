package com.ev.greenh.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.databinding.FragmentHomeBinding
import com.ev.greenh.home.data.HomeRepository
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.viewmodels.ViewModelFactory

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: HomeViewModel

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
        val repo = HomeRepository()
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        val activityViewModel = (activity as? MainActivity)?.activityViewModel
        viewModel.getTodayTip()
        binding?.cvHomeFrag?.setContent {
            HomeScreen(
                activityViewModel,
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