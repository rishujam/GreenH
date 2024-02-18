package com.example.auth.ui.states

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

sealed class SignUpProgress {
    object EnterPhoneStage : SignUpProgress()
    object VerifyPhoneStage : SignUpProgress()
    object VerifiedPhoneStage : SignUpProgress()
}