package com.ev.greenh.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.auth.ui.SignUpViewModel
import com.ev.greenh.auth.data.AuthRepository
import com.ev.greenh.grow.data.GrowRepository
import com.ev.greenh.grow.ui.LocalPlantListViewModel
import com.ev.greenh.repository.BaseRepository
import com.ev.greenh.repository.PlantRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PlantViewModel::class.java) -> PlantViewModel(repository as PlantRepository) as T
            modelClass.isAssignableFrom(OrderViewModel::class.java) -> OrderViewModel(repository as PlantRepository) as T
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(LocalPlantListViewModel::class.java) -> LocalPlantListViewModel(repository as GrowRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}