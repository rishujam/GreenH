package com.example.admin.ui.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Constants
import com.core.util.Resource
import com.core.util.toObject
import com.example.admin.data.repo.AdminRepository
import com.example.admin.ui.shop.model.PlantAdmin
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 29/11/24.
 */

class ShopHomeViewModel(
    private val repo: AdminRepository
) : ViewModel() {

    private val _searchResult = MutableStateFlow<PlantAdmin?>(null)
    val searchResult = _searchResult.asStateFlow()

    private val scope = MainScope()
    private var searchJob: Job? = null

    fun search(term: String) {
        searchJob?.cancel()
        searchJob = scope.launch {
            delay(500L)
            val res = repo.searchTerm(
                Constants.FirebaseColl.PLANTS,
                Constants.FirebaseField.NAME,
                term
            )
            val data = res?.toObject(PlantAdmin::class)
            _searchResult.emit(data)
        }
    }

}