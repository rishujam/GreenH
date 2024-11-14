package com.example.auth.ui.states

/*
 * Created by Sudhanshu Kumar on 13/11/24.
 */

sealed class SignUpOneTimeEvent {

    data class ShowToast(val msg: String) : SignUpOneTimeEvent()

}