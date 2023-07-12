package com.ev.greenh.auth.ui

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

    private val _state = mutableStateOf<SignUpState>(SignUpState())
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
            is SignUpEvents.Next -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }

            is SignUpEvents.OtpOption -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
                event.options
            }

            is SignUpEvents.Verify -> {

            }

            is SignUpEvents.ResendOtp -> {

            }

            is SignUpEvents.WrongNo -> {

            }

            is SignUpEvents.Loading -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
            }

            is SignUpEvents.NotLoading -> {
                _state.value = state.value.copy(
                    isLoading = false
                )
            }
        }
    }

    internal val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {
            _state.value = state.value.copy(
                isLoading = false
            )
            _eventFlow.tryEmit(SignUpUiEvents.ShowToast("Error"))
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            resendToken = token
            verifyId = verificationId
            _eventFlow.tryEmit(SignUpUiEvents.ScreenChanged(SignUpProgress.VerifyPhoneStage))
            //TODO - Show otp textfields and hide phone enter view, Request focus to first otp textfield, hide progress bar
            //TODO - Start resend otp timer
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