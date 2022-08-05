package com.ev.greenh.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.models.Profile
import com.ev.greenh.models.Response
import com.ev.greenh.repository.AuthRepository
import com.ev.greenh.util.Resource
import com.ev.greenh.util.ViewModelEventWrapper
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
):ViewModel() {

    private val _authResponse : MutableLiveData<Resource<Response>> = MutableLiveData()
    val authResponse:LiveData<Resource<Response>>
        get() = _authResponse

    private val _saveProfileRes : MutableLiveData<Resource<Response>> = MutableLiveData()
    val saveProfileRes:LiveData<Resource<Response>>
        get() = _saveProfileRes

    fun loginUser(
        email:String,
        pass:String
    ) = viewModelScope.launch {
        _authResponse.value = repository.login(email, pass)
    }

//    fun registerUser(
//        email: String,
//        pass: String
//    ) = viewModelScope.launch{
//        _authResponse.value = repository.registerUser(email, pass)
//    }

    fun saveUserProfile(collection: String,profile: Profile) = viewModelScope.launch {
        _authResponse.postValue(Resource.Loading())
        _authResponse.value = repository.saveUserProfile(collection, profile)
    }

    fun saveUIDLocally(uid: String) = viewModelScope.launch{
        repository.saveUidLocally(uid)
    }

    fun saveNotifyToken(uid:String,token:String,collection: String) = viewModelScope.launch {
        ViewModelEventWrapper(repository.saveNotifyToken(uid, token, collection))
    }
}