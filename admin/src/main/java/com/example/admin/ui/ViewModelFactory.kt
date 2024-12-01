package com.example.admin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.admin.data.repo.AdminRepository
import com.example.admin.ui.shop.ShopHomeViewModel
import com.example.admin.ui.shop.detail.EditPlantViewModel

/*
 * Created by Sudhanshu Kumar on 01/12/24.
 */

class ViewModelFactory(
    private val repository: AdminRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopHomeViewModel::class.java)) {
            return ShopHomeViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(EditPlantViewModel::class.java)) {
            return EditPlantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}