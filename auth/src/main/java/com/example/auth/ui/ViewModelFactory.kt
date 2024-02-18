package com.example.auth.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.data.remote.ApiIdentifier
import com.core.data.remote.RetrofitPool
import com.example.auth.data.localsource.UserDataPref
import com.example.auth.data.remotesource.PhoneAuthApi
import com.example.auth.data.remotesource.UserDataSource
import com.example.auth.data.repositoryimpl.PhoneAuthRepoImpl
import com.example.auth.data.repositoryimpl.UserDataRepoImpl
import com.example.auth.domain.usecase.OtpSignUpUseCase
import com.example.auth.domain.usecase.SendOtpUseCase
import java.lang.IllegalArgumentException

/*
 * Created by Sudhanshu Kumar on 10/02/24.
 */

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(
                SignUpViewModel::class.java
            ) -> {
                val api = RetrofitPool.getApi(
                    ApiIdentifier.TwoFactor,
                    PhoneAuthApi::class.java
                ).service as PhoneAuthApi
                val phoneAuthRepo = PhoneAuthRepoImpl(api)
                val userDataSource = UserDataSource()
                val userDataPref = UserDataPref(context)
                val userDataRepo = UserDataRepoImpl(userDataSource, userDataPref)
                val sendOtpUseCase = SendOtpUseCase(phoneAuthRepo)
                val otpSignUpUseCase = OtpSignUpUseCase(phoneAuthRepo, userDataRepo)
                SignUpViewModel(sendOtpUseCase, otpSignUpUseCase) as T
            }

            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}