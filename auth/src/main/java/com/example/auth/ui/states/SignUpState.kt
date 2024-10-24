package com.example.auth.ui.states

/*
 * Created by Sudhanshu Kumar on 13/07/23.
 */

data class SignUpState(
    var phoneNo: String = "",
    var screen: SignUpProgress = SignUpProgress.EnterPhoneStage,
    var loading: Boolean = false,
    var error: String? = null
)