package com.ev.greenh.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.data.localsource.UserDataPrefManager
import com.example.auth.data.repository.UserDataRepository
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
    private val userDataRepo: UserDataRepository
) : ViewModel() {

    var state by mutableStateOf(ProfileState())

    init {
        state = state.copy(isLoading = true)
        getProfileDetail()
    }

    fun onEvent(event: ProfileEvents) {
        when(event) {
            is ProfileEvents.ContactClick -> {
                state = state.copy(
                    contactExpanded = !state.contactExpanded
                )
            }
            else -> {}
        }
    }

    private fun getProfileDetail() = viewModelScope.launch(Dispatchers.IO) {
        val isLoggedIn = userDataRepo.isLoggedIn() ?: false
        withContext(Dispatchers.Main) {
            state = state.copy(
                isLoggedIn = isLoggedIn
            )
        }
        if(isLoggedIn) {
            val uid = userDataRepo.getUid()
            uid?.let {
                val result = userDataRepo.getUserData(uid)
                result.profile?.let {
                    state = state.copy(
                        profile = result.profile,
                        isLoading = false
                    )
                } ?: run {
                    state = state.copy(
                        errorMsg = "Unable to fetch profile",
                        isLoading = false
                    )
                }
            } ?: run {
                state = state.copy(
                    errorMsg = "Unable to fetch UID",
                    isLoading = false
                )
            }
        } else {
            state = state.copy(
                isLoading = false
            )
        }
    }

    private fun logout() {

    }

    private fun deleteAccount() {

    }

}