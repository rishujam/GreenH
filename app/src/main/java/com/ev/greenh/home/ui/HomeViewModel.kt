package com.ev.greenh.home.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import com.ev.greenh.home.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 15/11/23.
 */

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repo: HomeRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    fun x() {
        viewModelScope.launch {
            Log.d("ThreadTesting", "currentThread: ${Thread.currentThread().name} threadId: ${Thread.currentThread().id} context: ${this.coroutineContext}")
        }
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ThreadTesting", "currentThread IO: ${Thread.currentThread().name} threadId: ${Thread.currentThread().id} context: ${this.coroutineContext}")
        }
    }

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