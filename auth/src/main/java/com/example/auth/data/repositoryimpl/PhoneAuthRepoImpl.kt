package com.example.auth.data.repositoryimpl

import com.example.auth.data.model.ResSendOtp
import com.example.auth.data.remotesource.PhoneAuthApi
import com.example.auth.data.repository.PhoneAuthRepository
import com.example.auth.data.util.Constants
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
 * Created by Sudhanshu Kumar on 21/01/24.
 */

class PhoneAuthRepoImpl(
    private val api: PhoneAuthApi
) : PhoneAuthRepository {

    override suspend fun sendCredentials(phone: String): Flow<ResSendOtp?> = flow {
        val res = api.sendOtp(Constants.PHONE_AUTH_API_KEY, phone)
        if(res.isSuccessful) {
            emit(res.body())
        } else {
            emit(null)
        }
    }

    override suspend fun resendCredentials(
        phone: String,
        resendToken: PhoneAuthProvider.ForceResendingToken
    ): Flow<ResSendOtp?> {
        TODO("Not yet implemented")
    }

    override suspend fun verifyCredentials(verifyId: String, otp: String): String? {
        TODO("Not yet implemented")
    }
}