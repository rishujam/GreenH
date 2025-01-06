package com.example.ui.listing

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
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

@HiltViewModel
class PlantListViewModel @Inject constructor(
    private val plantListUseCase: GetPlantListUseCase
) : ViewModel() {

    private val defaultValue = Resource.Loading<List<Plant>>()
    private val _uiState = MutableStateFlow<Resource<List<Plant>>>(defaultValue)
    val uiState: StateFlow<Resource<List<Plant>>> = _uiState.asStateFlow()

    private var page: Int = 1

    fun getList(
        filters: List<PlantFilter>? = null
    ) = viewModelScope.launch {
        plantListUseCase.invoke(
            GetPlantsRequest(
                filters = filters,
                page = page
            )
        ).collect {
            if(it is Resource.Success) {
                page++
            }
            _uiState.emit(it)
        }
    }

    fun refresh() {
        page = 1
        _uiState.value = defaultValue
        getList()
    }

}