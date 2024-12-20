package com.ev.greenh.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.ui.model.AlertModel
import com.core.ui.model.AlertType
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
        getProfileDetail()
    }

    fun onEvent(
        event: ProfileEvents
    ) {
        when(event) {
            is ProfileEvents.Contact -> {
                state = state.copy(
                    contactExpanded = !state.contactExpanded
                )
            }

            is ProfileEvents.DeleteAccount -> {
                state = state.copy(
                    alert = AlertModel(
                        isShowing = true,
                        title = "Delete Profile",
                        message = "Are you sure you want to delete your profile",
                        cancelText = "Cancel",
                        confirmText = "Confirm",
                        type = AlertType.DeleteConfirmation
                    )
                )
            }

            is ProfileEvents.Logout -> {
                state = state.copy(
                    alert = AlertModel(
                        isShowing = true,
                        title = "Logout",
                        message = "Are you sure you want to logout.",
                        cancelText = "Cancel",
                        confirmText = "Confirm",
                        type = AlertType.LogoutConfirmation
                    )
                )
            }

            is ProfileEvents.AlertCancel -> {
                state = state.copy(
                    alert = null
                )
            }

            is ProfileEvents.DeleteAccountConfirm -> {
                deleteAccount()
            }

            is ProfileEvents.LogoutConfirm -> {
                logout(event.onCompletion)
            }
            else -> {}
        }
    }

    fun getProfileDetail() = viewModelScope.launch(Dispatchers.IO) {
        val isLoggedIn = userDataRepo.isLoggedIn() ?: false
        withContext(Dispatchers.Main) {
            state = state.copy(
                isLoggedIn = isLoggedIn,
                isLoading = true
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

    private fun logout(onCompletion: (() -> Unit)?) {
        state = state.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            userDataRepo.clearUserPref()
            withContext(Dispatchers.Main) {
                state = state.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    profile = null
                )
                onCompletion?.let { it() }
            }
        }
    }

    private fun deleteAccount() {
        state = state.copy(
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            val uid = if(!state.profile?.uid.isNullOrEmpty()) {
                state.profile?.uid
            } else {
                userDataRepo.getUid()
            }
            val result = userDataRepo.deleteUserData(uid)
            if(result.success) {
                userDataRepo.clearUserPref()
                withContext(Dispatchers.Main) {
                    state = state.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        profile = null
                    )
                }
            } else {
                //TODO Show error toast
            }
        }
    }

}