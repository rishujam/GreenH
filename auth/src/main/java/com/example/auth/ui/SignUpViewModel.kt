package com.example.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.util.Resource
import com.example.auth.data.model.UserProfile
import com.example.auth.domain.usecase.OtpSignUpUseCase
import com.example.auth.domain.usecase.SendOtpUseCase
import com.example.auth.ui.states.SignUpEvent
import com.example.auth.ui.states.SignUpOneTimeEvent
import com.example.auth.ui.states.SignUpProgress
import com.example.auth.ui.states.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val sendOtpUseCase: SendOtpUseCase,
    private val otpSignUpUseCase: OtpSignUpUseCase,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : ViewModel() {

    private val _oneTimeEvent = Channel<SignUpOneTimeEvent>()
    val oneTimeEvent: Flow<SignUpOneTimeEvent> = _oneTimeEvent.receiveAsFlow()

    var state by mutableStateOf(SignUpState())

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SendOtp -> {
                viewModelScope.launch(dispatcher) {
                    state = state.copy(
                        phoneNo = event.phone
                    )
                    sendOtpUseCase.invoke(event.phone).collect { response ->
                        when (response) {
                            is Resource.Error -> {
                                state = state.copy(
                                    loading = false
                                )
                                _oneTimeEvent.send(
                                    SignUpOneTimeEvent.ShowToast(response.message.orEmpty())
                                )
                            }

                            is Resource.Loading -> {
                                state = state.copy(
                                    loading = true
                                )
                            }

                            is Resource.Success -> {

                                state = state.copy(
                                    loading = false,
                                    screen = SignUpProgress.VerifyPhoneStage
                                )
                            }

                            else -> {}
                        }
                    }
                }
            }

            is SignUpEvent.WrongNo -> {
                state = state.copy(
                    phoneNo = "",
                    screen = SignUpProgress.EnterPhoneStage
                )
            }

            is SignUpEvent.VerifyClick -> {
                viewModelScope.launch(dispatcher) {
                    otpSignUpUseCase.invoke(state.phoneNo, event.otp, event.buildVersion)
                        .collect { response ->
                            when (response) {
                                is Resource.Error -> {
                                    state = state.copy(
                                        loading = false
                                    )
                                    _oneTimeEvent.send(
                                        SignUpOneTimeEvent.ShowToast(response.message.orEmpty())
                                    )
                                }

                                is Resource.Success -> {
                                    state = state.copy(
                                        loading = false,
                                        screen = SignUpProgress.VerifiedPhoneStage(
                                            profile = UserProfile(
                                                phone = state.phoneNo
                                            )
                                        )
                                    )
                                }

                                is Resource.Loading -> {
                                    state = state.copy(
                                        loading = true
                                    )
                                }

                                else -> {}
                            }
                        }
                }
            }

            is SignUpEvent.ResendOtp -> {
                state = state.copy(
                    isTimerRunning = true
                )
            }

            is SignUpEvent.ShowResendButton -> {
                state = state.copy(
                    isTimerRunning = false
                )
            }

            else -> {}
        }
    }

}