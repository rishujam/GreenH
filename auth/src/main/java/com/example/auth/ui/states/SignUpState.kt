package com.example.auth.ui.states

/*
 * Created by Sudhanshu Kumar on 13/07/23.
 */

data class SignUpState(
    val phoneNo: String = "",
    val screen: SignUpProgress = SignUpProgress.EnterPhoneStage,
    val loading: Boolean = false,
    val error: String? = null,
    val isTimerRunning: Boolean = true
)