package com.ev.greenh.auth.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.auth.ui.events.SignUpEvents
import com.ev.greenh.auth.ui.events.SignUpUiEvents
import com.ev.greenh.auth.ui.states.SignUpProgress
import com.ev.greenh.auth.ui.states.SignUpState
import com.ev.greenh.models.Profile
import com.ev.greenh.repository.AuthRepository
import com.ev.greenh.services.FirebaseNotify
import com.ev.greenh.util.Resource
import com.ev.greenh.util.ViewModelEventWrapper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
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

    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var verifyId: String? = null
    private var uid: String? = null

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.NextClick -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        phoneNo = event.phone
                    )
                    _eventFlow.emit(SignUpUiEvents.Loading(true))
                }
            }

            is SignUpEvents.OtpOption -> {
                if (event.phone != null) {
                    _state.value = state.value.copy(
                        phoneNo = event.phone.toString()
                    )
                    event.options?.let {
                        PhoneAuthProvider.verifyPhoneNumber(it)
                    }
                } else {
                    viewModelScope.launch {
                        _eventFlow.emit(SignUpUiEvents.Loading(false))
                        _eventFlow.emit(SignUpUiEvents.ShowToast(event.message.toString()))
                    }
                }
            }

            is SignUpEvents.VerifyClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(SignUpUiEvents.Loading(true))
                    verifyUser(event.otp, event.userRef, event.tokenRef)
                }
            }

            is SignUpEvents.ResendOtp -> {
                viewModelScope.launch {
                    event.options?.let {
                        _eventFlow.emit(SignUpUiEvents.Loading(true))
                        PhoneAuthProvider.verifyPhoneNumber(event.options)
                    }
                }
            }

            is SignUpEvents.WrongNo -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        phoneNo = ""
                    )
                    _eventFlow.emit(SignUpUiEvents.ScreenChanged(SignUpProgress.EnterPhoneStage))
                }
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

    private fun verifyUser(otp: String, userColl: String, tokenColl: String) {
        val auth = FirebaseAuth.getInstance()
        verifyId?.let {
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                it, otp
            )
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid.toString()
                        this.uid = uid
                        val profile = Profile(phone = state.value.phoneNo, uid = uid)
                        saveUserProfile(userColl, profile, tokenColl)
                    } else {
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            viewModelScope.launch {
                                _eventFlow.emit(SignUpUiEvents.Loading(false))
                                _eventFlow.emit(SignUpUiEvents.ShowToast("Invalid OTP"))
                            }
                        }
                    }
                }
        }
    }

    private fun saveUserProfile(collection: String, profile: Profile, tokenColl: String) =
        viewModelScope.launch {
            val authRes = authRepository.saveUserProfile(collection, profile)
            when (authRes) {
                is Resource.Success -> {
                    saveNotifyToken(tokenColl)
                    saveUIDLocally()
                    _eventFlow.emit(SignUpUiEvents.ScreenChanged(SignUpProgress.VerifiedPhoneStage))
                }

                is Resource.Error -> {
                    _eventFlow.emit(SignUpUiEvents.Loading(false))
                    _eventFlow.emit(SignUpUiEvents.ShowToast("Unable to Sign Up"))
                }

                else -> {}
            }

        }

    private fun saveUIDLocally() = viewModelScope.launch {
        uid?.let {
            authRepository.saveUidLocally(it)
        }
    }

    private fun saveNotifyToken(collection: String) =
        viewModelScope.launch {
            uid?.let {
                var token = ""
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    token = task.result.toString()
                    FirebaseNotify.token = token
                    Log.e("token", token)
                })
                ViewModelEventWrapper(authRepository.saveNotifyToken(it, token, collection))
            }
        }

}