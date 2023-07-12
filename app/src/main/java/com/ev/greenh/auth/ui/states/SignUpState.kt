package com.ev.greenh.auth.ui.states

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

data class SignUpState(
    var phoneNo: String = "",
    var otp: String = "",
    var isLoading: Boolean = true
)