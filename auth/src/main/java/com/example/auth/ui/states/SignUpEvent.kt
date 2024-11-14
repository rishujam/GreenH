package com.example.auth.ui.states

import com.example.auth.data.model.UserProfile

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

sealed class SignUpEvent {

    /** Requesting for OTP */
    data class SendOtp(val phone: String) : SignUpEvent()

    /** User entered the OTP and clicked signup */
    data class VerifyClick(val otp: String, val buildVersion: Int) :
        SignUpEvent()

    object ResendOtp : SignUpEvent()

    /** User wants to go back to enter phone screen */
    object WrongNo : SignUpEvent()

    data class SignUpSuccess(val profile: UserProfile) : SignUpEvent()

    object ShowResendButton : SignUpEvent()
}