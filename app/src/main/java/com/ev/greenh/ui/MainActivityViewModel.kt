package com.ev.greenh.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.localstorage.ConfigDatabase
import com.core.data.model.Feature
import com.core.data.pref.ConfigPref
import com.core.util.Resource
import com.ev.greenh.BuildConfig
import com.ev.greenh.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 13/02/24.
 */

class MainActivityViewModel(
    private val configPref: ConfigPref,
    private val configDB: ConfigDatabase
) : ViewModel() {

    private val _config = MutableSharedFlow<Resource<MainActivityState>>()
    val config = _config.asSharedFlow()

    fun load() = viewModelScope.launch(Dispatchers.IO) {
        _config.emit(Resource.Loading())
        val maintenance = configPref.readMaintenance() ?: false
        var updateDialog = false
        val forceUpdate = configPref.readForceUpdate()
        val latestVersion = configPref.readLatestVersion()
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