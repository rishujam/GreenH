package com.example.ui.listing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import com.example.domain.model.GetPlantsRequest
import com.example.domain.model.Plant
import com.example.domain.model.PlantFilter
import com.example.domain.usecase.GetPlantListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

@HiltViewModel
class PlantListViewModel @Inject constructor(
    private val plantListUseCase: GetPlantListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<Plant>>?>(null)
    val uiState: StateFlow<Resource<List<Plant>>?> = _uiState.asStateFlow()

    private var page: Int = 1
    private var lastPageReached = false
    private var isApiInProgress = false
    private var currentList = mutableListOf<Plant>()

    fun getList(
        filters: List<PlantFilter>? = null
    ) = viewModelScope.launch {
        if(lastPageReached || isApiInProgress) {
            return@launch
        }
        Log.d("RishuTest", "calling api offset: $page")
        _uiState.emit(Resource.Loading())
        isApiInProgress = true
        plantListUseCase.invoke(
            GetPlantsRequest(
                filters = filters,
                page = page
            )
        ).collectLatest {
            when(it) {
                is Resource.Error -> {
                    isApiInProgress = false
                    _uiState.emit(it)
                }
                is Resource.Success -> {
                    isApiInProgress = false
                    page++
                    it.data?.let { data ->
                        if(data.size < 5) {
                            lastPageReached = true
                        }
                    }
                    currentList.addAll(it.data.orEmpty())
                    _uiState.emit(Resource.Success(currentList.toList()))
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun refresh() {
        page = 1
        _uiState.value = null
        lastPageReached = false
        currentList.clear()
        getList()
    }

}