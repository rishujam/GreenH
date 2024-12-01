package com.example.admin.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.core.ui.show
import com.example.admin.data.data_source.FirebaseDataSource
import com.example.admin.data.repo.AdminRepository
import com.example.admin.data.repo.AdminRepositoryFirebaseImpl
import com.example.admin.databinding.FragmentAdminShopBinding
import com.example.admin.ui.AdminActivity
import com.example.admin.ui.ViewModelFactory
import com.example.admin.ui.shop.detail.EditPlantFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 25/11/24.
 */

class ShopAdminFragment : Fragment() {

    private var _binding: FragmentAdminShopBinding?=null
    private val binding get() = _binding
    private lateinit var viewModel: ShopHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminShopBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fireStoreDataSource = FirebaseDataSource()
        val repo: AdminRepository = AdminRepositoryFirebaseImpl(fireStoreDataSource)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[ShopHomeViewModel::class.java]
        lifecycleScope.launch {
            viewModel.searchResult.collectLatest { result ->
                result?.let {
                    binding?.apply {
                        grpResult.show()
                        tvPriceAdminShop.text = it.price.toString()
                        tvNameAdminShop.text = it.name
                        Glide.with(root).load(result.imageUrl).into(ivPlantAdminShop)
                    }
                }
            }
        }
        binding?.etSearchAdminShop?.addTextChangedListener {
            if(it.toString().isNotEmpty()) {
                viewModel.search(it.toString())
            }
        }

        binding?.fabAddPlant?.setOnClickListener {
            val frag = EditPlantFragment()
            (activity as? AdminActivity)?.setCurrentFragmentBack(frag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}