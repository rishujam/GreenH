package com.ev.greenh.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.data.localsource.UserDataPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 29/10/24.
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDataPref: UserDataPrefManager
) : ViewModel() {

    var state by mutableStateOf(ProfileState())

    init {
        state = state.copy(isLoading = true)
        getLoggedInStatus()
    }

    private fun getLoggedInStatus() = viewModelScope.launch(Dispatchers.IO) {
        val isLoggedIn = userDataPref.isLoggedIn()
        withContext(Dispatchers.Main) {
            state = state.copy(
                isLoggedIn = isLoggedIn,
                isLoading = false
            )
        }
    }

}