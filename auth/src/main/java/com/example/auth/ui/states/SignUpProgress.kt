package com.example.auth.ui.states

import com.example.auth.data.model.UserProfile

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

sealed class SignUpProgress {
    object EnterPhoneStage : SignUpProgress()
    object VerifyPhoneStage : SignUpProgress()
    data class VerifiedPhoneStage(val profile: UserProfile) : SignUpProgress()
}