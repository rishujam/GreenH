package com.ev.greenh.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.models.Response
import com.ev.greenh.repository.AuthRepository
import com.ev.greenh.util.Resource
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
):ViewModel() {

    private val _authResponse : MutableLiveData<Resource<Response>> = MutableLiveData()
    val authResponse:LiveData<Resource<Response>>
        get() = _authResponse

    fun loginUser(
        email:String,
        pass:String
    )= viewModelScope.launch {
        _authResponse.value = repository.login(email, pass)
    }

    fun registerUser(
        email: String,
        pass: String
    ) = viewModelScope.launch{
        _authResponse.value = repository.registerUser(email, pass)
    }

    fun saveEmailLocally(email: String) = viewModelScope.launch{
        repository.saveAuthEmail(email)
    }

    fun readEmail() = viewModelScope.launch {
        repository.readEmail()
    }
}