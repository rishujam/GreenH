package com.example.auth.ui.events

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

sealed class SignUpEvents {

    /** Requesting for OTP */
    data class SendOtp(val phone: String) : SignUpEvents()

    /** User entered the OTP and clicked signup */
    data class VerifyClick(val otp: String, val buildVersion: Int) :
        SignUpEvents()

    object ResendOtp : SignUpEvents()

    /** User wants to go back to enter phone screen */
    object WrongNo : SignUpEvents()
}