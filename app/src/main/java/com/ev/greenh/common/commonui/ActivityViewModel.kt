package com.ev.greenh.common.commonui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.common.commondata.AppStartupRepository
import com.ev.greenh.common.commonui.event.ActivityEvent
import com.ev.greenh.common.commonui.state.ActivityState
import com.ev.greenh.util.Constants
import com.ev.greenh.util.Resource
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 12/11/23.
 */

class ActivityViewModel(
    private val repo: AppStartupRepository
) : ViewModel() {

    var state by mutableStateOf(ActivityState(features = emptyMap()))

    fun onEvent(event: ActivityEvent) {
        when(event) {
            is ActivityEvent.ShowDialog -> {
                state = state.copy(
                    showDialog = event.dialog
                )
            }
        }
    }

    fun getFeatureList() = viewModelScope.launch {
        val list = repo.getFeaturesList()
        when(list) {
            is Resource.Success -> {
                list.data?.list?.let {
                    val map = mutableMapOf<String, Boolean>()
                    //TODO Improve this logic
                    for(i in it) {
                        if(i.id != null) {
                            map[i.id] = i.isEnabled
                        }
                    }
                    state = state.copy(
                        features = map
                    )
                }

            }
            is Resource.Error -> {
                state = state.copy(
                    features = Constants.getFallbackFeatureMap()
                )
            }
            is Resource.Loading -> {    }
        }
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