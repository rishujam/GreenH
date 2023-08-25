package com.ev.greenh.auth.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ev.greenh.auth.data.AuthRepository
import com.ev.greenh.auth.ui.events.SignUpEvents
import com.ev.greenh.auth.ui.states.SignUpProgress
import com.ev.greenh.auth.ui.states.SignUpState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(SignUpState())

    private val _toastEvent = mutableStateOf<String?>(null)
    val toastEvent: State<String?> = _toastEvent

    var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    var verifyId: String? = null

    fun getAuth(): FirebaseAuth {
        return authRepository.getAuth()
    }

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.NextClick -> {
                state = state.copy(
                    phoneNo = event.phone,
                    loading = true
                )
            }

            is SignUpEvents.OtpOption -> {
                if (event.phone != null) {
                    state = state.copy(
                        phoneNo = event.phone.toString()
                    )
                    authRepository.sendOtp(event.options)
                } else {
                    state = state.copy(
                        loading = false
                    )
                    _toastEvent.value = event.message.toString()
                }
            }

            is SignUpEvents.VerifyClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    state = state.copy(
                        loading = true
                    )
                    val verifyResult = authRepository.verifyUser(
                        event.otp, verifyId,
                        state.phoneNo,
                        event.userRef,
                        event.tokenRef
                    )
                    if(verifyResult) {
                        state = state.copy(
                            screen = SignUpProgress.VerifiedPhoneStage
                        )
                    } else {
                        state = state.copy(
                            loading = false
                        )
                        _toastEvent.value = "Invalid OTP"
                    }
                }
            }

            is SignUpEvents.ResendOtp -> {
                event.options?.let {
                    state = state.copy(
                        loading = true
                    )
                    authRepository.sendOtp(event.options)
                }
            }

            is SignUpEvents.WrongNo -> {
                state = state.copy(
                    phoneNo = "",
                    screen = SignUpProgress.EnterPhoneStage
                )
            }
        }
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

        override fun onVerificationFailed(e: FirebaseException) {
            state = state.copy(
                loading = false
            )
            _toastEvent.value = "Error"
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            resendToken = token
            verifyId = verificationId
            state = state.copy(
                loading = false,
                screen = SignUpProgress.VerifyPhoneStage
            )
        }
    }

}