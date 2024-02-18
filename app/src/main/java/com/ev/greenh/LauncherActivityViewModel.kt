package com.ev.greenh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

class LauncherActivityViewModel(
    private val appStartUpRecipeUseCase: GetAppStartUpRecipeUseCase
) : ViewModel() {

    private val _appInitialisation = MutableSharedFlow<Boolean>()
    val appInitialisation = _appInitialisation.asSharedFlow()

    fun getRecipeForStartUp() = viewModelScope.launch(Dispatchers.IO) {
        appStartUpRecipeUseCase.invoke().collect {
            _appInitialisation.emit(true)
        }
    }

}