package com.ev.greenh.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.localstorage.ConfigDatabase
import com.core.data.model.Feature
import com.core.data.pref.ConfigPrefManager
import com.core.util.Resource
import com.ev.greenh.BuildConfig
import com.ev.greenh.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 13/02/24.
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val configPrefManager: ConfigPrefManager,
    private val configDB: ConfigDatabase
) : ViewModel() {

    private val _config = MutableSharedFlow<Resource<MainActivityState>>()
    val config = _config.asSharedFlow()

    fun load() = viewModelScope.launch(Dispatchers.IO) {
        _config.emit(Resource.Loading())
        val maintenance = configPrefManager.readMaintenance() ?: false
        var updateDialog = false
        val forceUpdate = configPrefManager.readForceUpdate()
        val latestVersion = configPrefManager.readLatestVersion()
        val isOldVersion = latestVersion > BuildConfig.VERSION_CODE
        if(forceUpdate && isOldVersion) {
            updateDialog = true
        }
        val map = mutableMapOf<String, Feature?>()
        map[Constants.Feature.IDENTIFY] = configDB.dao.getFeatureConfig(Constants.Feature.IDENTIFY)
        map[Constants.Feature.SHOP] = configDB.dao.getFeatureConfig(Constants.Feature.SHOP)
        map[Constants.Feature.GROW] = configDB.dao.getFeatureConfig(Constants.Feature.GROW)
        _config.emit(Resource.Success(MainActivityState(updateDialog, maintenance, map)))
    }

}