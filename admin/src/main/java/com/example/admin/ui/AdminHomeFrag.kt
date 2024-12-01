package com.example.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.databinding.FragmentAdminHomeBinding
import com.example.admin.ui.shop.ShopAdminFragment

/*
 * Created by Sudhanshu Kumar on 25/11/24.
 */

class AdminHomeFrag : Fragment() {

    private var _binding: FragmentAdminHomeBinding?=null
    private val binding get() = _binding
    private lateinit var homeAdapter: HomeAdapter

    companion object {
        private const val ADMIN_SHOP = "ADMIN_SHOP"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter = HomeAdapter()
        homeAdapter.addData(listOf(ADMIN_SHOP))
        binding?.rvAdminHome?.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(context)
        }
        homeAdapter.setOnItemClickListener { title ->
            if(title == ADMIN_SHOP) {
                (activity as? AdminActivity)?.setCurrentFragmentBack(ShopAdminFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}