package com.ev.greenh.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/*
 * Created by Sudhanshu Kumar on 10/07/23.
 */

class SignUpViewModel : ViewModel() {

    private val _phoneNo = MutableStateFlow("")
    val phoneNo = _phoneNo.asStateFlow()

}