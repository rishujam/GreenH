package com.ev.greenh.auth.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.auth.ui.events.SignUpEvents
import com.ev.greenh.auth.ui.events.SignUpUiEvents
import com.ev.greenh.auth.ui.states.SignUpProgress
import com.ev.greenh.auth.ui.states.SignUpState
import com.ev.greenh.models.Profile
import com.ev.greenh.models.Response
import com.ev.greenh.repository.AuthRepository
import com.ev.greenh.util.Resource
import com.ev.greenh.util.ViewModelEventWrapper
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    private val _eventFlow = MutableSharedFlow<SignUpUiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _authResponse: MutableLiveData<Resource<Response>> = MutableLiveData()
    val authResponse: LiveData<Resource<Response>>
        get() = _authResponse

    private val _saveProfileRes: MutableLiveData<Resource<Response>> = MutableLiveData()
    val saveProfileRes: LiveData<Resource<Response>>
        get() = _saveProfileRes

    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var verifyId: String? = null

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.NextClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(SignUpUiEvents.Loading(true))
                }
            }

            is SignUpEvents.OtpOption -> {
                if(event.options != null) {
                    _state.value = state.value.copy(
                        phoneNo = event.phone.toString()
                    )
                    PhoneAuthProvider.verifyPhoneNumber(event.options)
                } else {
                    viewModelScope.launch {
                        _eventFlow.emit(SignUpUiEvents.Loading(false))
                        _eventFlow.emit(SignUpUiEvents.ShowToast("Error in building options"))
                    }
                }
            }

            is SignUpEvents.VerifyClick -> {

            }

            is SignUpEvents.ResendOtp -> {

            }

            is SignUpEvents.WrongNo -> {

            }
        }
    }

    internal val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {
            viewModelScope.launch {
                _eventFlow.emit(SignUpUiEvents.Loading(false))
                _eventFlow.emit(SignUpUiEvents.ShowToast("Error"))
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            resendToken = token
            verifyId = verificationId
            viewModelScope.launch {
                _eventFlow.emit(SignUpUiEvents.Loading(false))
                _eventFlow.emit(SignUpUiEvents.ScreenChanged(SignUpProgress.VerifyPhoneStage))
            }
        }
    }

    fun saveUserProfile(collection: String, profile: Profile) = viewModelScope.launch {
        _saveProfileRes.postValue(Resource.Loading())
        val authRes = authRepository.saveUserProfile(collection, profile)
        _saveProfileRes.postValue(authRes)
    }

    fun saveUIDLocally(uid: String) = viewModelScope.launch {
        authRepository.saveUidLocally(uid)
    }

    fun saveNotifyToken(uid: String, token: String, collection: String) = viewModelScope.launch {
        ViewModelEventWrapper(authRepository.saveNotifyToken(uid, token, collection))
    }

}