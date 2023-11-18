package com.ev.greenh.common.commonui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.BuildConfig
import com.ev.greenh.common.commondata.AppStartupRepository
import com.ev.greenh.common.commonui.event.ActivityEvent
import com.ev.greenh.common.commonui.model.AppConfigUI
import com.ev.greenh.common.commonui.state.ActivityState
import com.ev.greenh.util.Constants
import com.ev.greenh.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 12/11/23.
 */

class ActivityViewModel(
    private val repo: AppStartupRepository
) : ViewModel() {

    var state by mutableStateOf(ActivityState(features = emptyMap()))

    fun onEvent(event: ActivityEvent) {
        when (event) {
            is ActivityEvent.ShowDialog -> {

            }
        }
    }

    fun getConfigData() = viewModelScope.launch(Dispatchers.IO) {
        val featuresDef = async {
            getFeatureList()
        }
        val configDef = async {
            getAppConfig()
        }
        val features = featuresDef.await()
        val config = configDef.await()

        state = state.copy(
            features = features ?: emptyMap(),
            maintenance = config?.maintenance ?: false,
            isUpdateRequired = config?.isUpdateRequired ?: false
        )
    }

    private suspend fun getFeatureList(): Map<String, Boolean>? {
        val list = repo.getFeaturesList()
        if(list is Resource.Success) {
            list.data?.list?.let {
                val map = mutableMapOf<String, Boolean>()
                //TODO Improve this logic
                for (i in it) {
                    if (i.id != null) {
                        map[i.id] = i.isEnabled
                    }
                }
                return map
            }
        }
        return null
    }

    private suspend fun getAppConfig(): AppConfigUI? {
        val result = repo.getAppConfig()
        if(result is Resource.Success) {
            var isUpdateRequired = false
            result.data?.latestVersion?.let {
                val forceUpdate = result.data.forceUpdate
                if (it > BuildConfig.VERSION_CODE && forceUpdate) {
                    isUpdateRequired = true
                }
            }
            return AppConfigUI(
                isUpdateRequired,
                result.data?.maintenance ?: false
            )
        }
        return null
    }

    fun dismissDialog() {
        state = state.copy(
            showDialog = null
        )
    }

    fun isFeatureEnabled(feature: String): Boolean {
        return state.features.getOrDefault(feature, false)
    }

}