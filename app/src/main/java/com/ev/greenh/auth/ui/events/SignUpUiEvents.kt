package com.ev.greenh.auth.ui.events

import com.ev.greenh.auth.ui.states.SignUpProgress

/*
 * Created by Sudhanshu Kumar on 13/07/23.
 */

sealed class SignUpUiEvents {
    data class ShowToast(val message: String) : SignUpUiEvents()
    data class ScreenChanged(val screen: SignUpProgress) : SignUpUiEvents()
    data class Loading(val isLoading: Boolean) : SignUpUiEvents()
}