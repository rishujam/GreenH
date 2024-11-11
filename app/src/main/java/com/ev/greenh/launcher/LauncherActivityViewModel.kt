package com.ev.greenh.launcher

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

@HiltViewModel
class LauncherActivityViewModel @Inject constructor(
    private val appStartUpRecipeUseCase: GetAppStartUpRecipeUseCase,
    private val userDataRepo: UserDataRepository
) : ViewModel() {

    var state by mutableStateOf(LauncherState())

    init {
        isLoggedInOrSkipped()
        getRecipeForStartUp()
    }

    fun onEvent(event: LauncherEvent, onCompletion: (() -> Unit)? = null) {
        when(event) {
            is LauncherEvent.ConfigLoaded -> {
                state = state.copy(
                    configLoaded = true
                )
            }

            is LauncherEvent.Skip -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userDataRepo.setLoginSkipped()
                    withContext(Dispatchers.Main) {
                        onCompletion?.let { it() }
                    }
                }
            }

            else -> {}
        }
    }

    private fun isLoggedInOrSkipped() = viewModelScope.launch(Dispatchers.IO) {
        val isLoggedIn = userDataRepo.isLoggedIn()
        val isSkipped = userDataRepo.isLoginSkipped()
        withContext(Dispatchers.Main) {
            state = state.copy(
                isLoggedIn = isLoggedIn,
                isLoginSkippedEarlier = isSkipped
            )
        }
    }

    private fun getRecipeForStartUp() = viewModelScope.launch(Dispatchers.IO) {
        appStartUpRecipeUseCase.invoke().collectLatest {
            state = state.copy(
                configLoaded = it
            )
        }
    }

}