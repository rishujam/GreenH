package com.ev.greenh.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ev.greenh.analytics.AnalyticsRepo
import com.ev.greenh.auth.ui.SignUpViewModel
import com.ev.greenh.auth.data.AuthRepository
import com.ev.greenh.common.commondata.AppStartupRepository
import com.ev.greenh.common.commonui.ActivityViewModel
import com.ev.greenh.grow.data.GrowRepository
import com.ev.greenh.grow.ui.GrowViewModel
import com.ev.greenh.grow.ui.LocalPlantListViewModel
import com.ev.greenh.home.data.HomeRepository
import com.ev.greenh.home.ui.HomeViewModel
import com.ev.greenh.plantidentify.data.repo.PlantIdentifyRepo
import com.ev.greenh.plantidentify.doamin.usecase.PlantIdentifyUseCase
import com.ev.greenh.plantidentify.ui.PlantIdentifyViewModel
import com.ev.greenh.repository.BaseRepository
import com.ev.greenh.repository.PlantRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(
                PlantViewModel::class.java
            ) -> PlantViewModel(repository as PlantRepository) as T

            modelClass.isAssignableFrom(
                OrderViewModel::class.java
            ) -> OrderViewModel(repository as PlantRepository) as T

            modelClass.isAssignableFrom(
                SignUpViewModel::class.java
            ) -> SignUpViewModel(repository as AuthRepository) as T

            modelClass.isAssignableFrom(
                LocalPlantListViewModel::class.java
            ) -> LocalPlantListViewModel(repository as GrowRepository) as T

            modelClass.isAssignableFrom(
                GrowViewModel::class.java
            ) -> GrowViewModel(repository as GrowRepository) as T

            modelClass.isAssignableFrom(
                PlantIdentifyViewModel::class.java
            ) ->  {
                val useCase = PlantIdentifyUseCase(repository as PlantIdentifyRepo)
                val analyticRepo = AnalyticsRepo()
                PlantIdentifyViewModel(useCase, analyticRepo) as T
            }

            modelClass.isAssignableFrom(
                ActivityViewModel::class.java
            ) -> ActivityViewModel(repository as AppStartupRepository) as T

            modelClass.isAssignableFrom(
                HomeViewModel::class.java
            ) -> HomeViewModel(repository as HomeRepository) as T

            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}