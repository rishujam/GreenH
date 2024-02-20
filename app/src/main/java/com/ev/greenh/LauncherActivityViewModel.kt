package com.ev.greenh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

@HiltViewModel
class LauncherActivityViewModel @Inject constructor(
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