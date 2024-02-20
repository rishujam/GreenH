package com.example.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import com.example.auth.domain.usecase.OtpSignUpUseCase
import com.example.auth.domain.usecase.SendOtpUseCase
import com.example.auth.ui.events.SignUpEvents
import com.example.auth.ui.states.SignUpProgress
import com.example.auth.ui.states.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val otpSignUpUseCase: OtpSignUpUseCase
) : ViewModel() {

    var state by mutableStateOf(SignUpState())

    var toastEvent by mutableStateOf("")

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.SendOtp -> {
                viewModelScope.launch(Dispatchers.IO) {
                    state = state.copy(
                        phoneNo = event.phone
                    )
                    sendOtpUseCase.invoke(event.phone).collect { response ->
                        when (response) {
                            is Resource.Error -> {
                                response.message?.let { errorMsg ->
                                    toastEvent = errorMsg
                                }
                            }

                            is Resource.Loading -> {
                                state = state.copy(
                                    loading = true
                                )
                            }

                            is Resource.Success -> {
                                response.data
                                state = state.copy(
                                    loading = false,
                                    screen = SignUpProgress.VerifyPhoneStage
                                )
                            }
                        }
                    }
                }
            }

            is SignUpEvents.WrongNo -> {
                state = state.copy(
                    phoneNo = "",
                    screen = SignUpProgress.EnterPhoneStage
                )
            }

            is SignUpEvents.VerifyClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    otpSignUpUseCase.invoke(state.phoneNo, event.otp, event.buildVersion).collect { response ->
                        when (response) {
                            is Resource.Error -> {
                                response.message?.let { toastEvent = it }
                            }

                            is Resource.Success -> {
                                state = state.copy(
                                    loading = false,
                                    screen = SignUpProgress.VerifiedPhoneStage
                                )
                            }

                            is Resource.Loading -> {
                                state = state.copy(
                                    loading = true
                                )
                            }
                        }
                    }
                }
            }

            is SignUpEvents.ResendOtp -> {

            }
        }
    }

}