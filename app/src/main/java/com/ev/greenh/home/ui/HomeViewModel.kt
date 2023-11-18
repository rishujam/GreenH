package com.ev.greenh.home.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.home.data.HomeRepository
import com.ev.greenh.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 15/11/23.
 */

class HomeViewModel(
    private val repo: HomeRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    fun getTodayTip() = viewModelScope.launch(Dispatchers.IO) {
        val tip = repo.getTodayTip()
        when(tip) {
            is Resource.Success -> {
                state = state.copy(
                    tip = tip.data.toString()
                )
            }
            is Resource.Error -> {
                state = state.copy(
                    tip = ""
                )
            }
            is Resource.Loading -> {}
        }
    }

}