package com.ev.greenh.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import com.example.auth.data.model.ResSaveProfile
import com.example.auth.data.model.UserProfile
import com.example.auth.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 04/11/24.
 */

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userDataRepo: UserDataRepository
) : ViewModel() {

    private val _updateProfile = MutableSharedFlow<Resource<ResSaveProfile>>()
    val updateProfile = _updateProfile.asSharedFlow()

    fun updateProfile(profile: UserProfile) = viewModelScope.launch(Dispatchers.IO) {
        _updateProfile.emit(Resource.Loading())
        val result = userDataRepo.saveUserData(profile)
        withContext(Dispatchers.Main) {
            if(result.success) {
                _updateProfile.emit(Resource.Success(result))
            } else {
                _updateProfile.emit(Resource.Error(result.msg))
            }
        }
    }

}