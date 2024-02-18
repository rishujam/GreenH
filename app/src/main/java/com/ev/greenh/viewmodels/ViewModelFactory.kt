package com.ev.greenh.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.data.AppConfigRepositoryImpl
import com.core.data.localstorage.ConfigDatabase
import com.core.data.pref.ConfigPref
import com.core.data.remote.ConfigDataSource
import com.ev.greenh.GetAppStartUpRecipeUseCase
import com.ev.greenh.LauncherActivityViewModel
import com.ev.greenh.analytics.AnalyticsRepo
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
import com.ev.greenh.ui.MainActivityState
import com.ev.greenh.ui.MainActivityViewModel

class ViewModelFactory(
    private val repository: BaseRepository? = null,
    private val context: Context
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
                LocalPlantListViewModel::class.java
            ) -> LocalPlantListViewModel(repository as GrowRepository) as T

            modelClass.isAssignableFrom(
                GrowViewModel::class.java
            ) -> GrowViewModel(repository as GrowRepository) as T

            modelClass.isAssignableFrom(
                PlantIdentifyViewModel::class.java
            ) -> {
                val useCase = PlantIdentifyUseCase(repository as PlantIdentifyRepo)
                val analyticRepo = AnalyticsRepo()
                PlantIdentifyViewModel(useCase, analyticRepo) as T
            }

            modelClass.isAssignableFrom(
                LauncherActivityViewModel::class.java
            ) -> {
                val configDataSource = ConfigDataSource()
                val configPref = ConfigPref(context)
                val configDb = ConfigDatabase.invoke(context)
                val appConfigRepo = AppConfigRepositoryImpl(
                    configDataSource,
                    configPref,
                    configDb
                )
                val startUpRecipeUseCase = GetAppStartUpRecipeUseCase(appConfigRepo)
                LauncherActivityViewModel(startUpRecipeUseCase) as T
            }

            modelClass.isAssignableFrom(
                HomeViewModel::class.java
            ) -> HomeViewModel(repository as HomeRepository) as T

            modelClass.isAssignableFrom(
                MainActivityViewModel::class.java
            ) -> {
                val configPref = ConfigPref(context)
                val configDb = ConfigDatabase.invoke(context)
                MainActivityViewModel(configPref, configDb) as T
            }

            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}