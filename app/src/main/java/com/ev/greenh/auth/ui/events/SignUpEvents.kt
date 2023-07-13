package com.ev.greenh.auth.ui.events

import com.google.firebase.auth.PhoneAuthOptions

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

sealed class SignUpEvents {
    object NextClick : SignUpEvents()
    data class VerifyClick(val otp: String, val userRef: String, val tokenRef: String) :
        SignUpEvents()

    data class ResendOtp(val options: PhoneAuthOptions?) : SignUpEvents()
    object WrongNo : SignUpEvents()
    data class OtpOption(val options: PhoneAuthOptions?, val message: String?, val phone: String?) :
        SignUpEvents()
}