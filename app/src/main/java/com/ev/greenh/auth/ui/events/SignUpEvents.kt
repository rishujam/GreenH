package com.ev.greenh.auth.ui.events

import com.google.firebase.auth.PhoneAuthOptions

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

sealed class SignUpEvents {
    object Next : SignUpEvents()
    data class Verify(val otp: String) : SignUpEvents()
    data class ResendOtp(val phoneNo: String) : SignUpEvents()
    object WrongNo : SignUpEvents()
    object Loading : SignUpEvents()
    object NotLoading : SignUpEvents()
    data class OtpOption(val options: PhoneAuthOptions?, val message: String?) : SignUpEvents()
}