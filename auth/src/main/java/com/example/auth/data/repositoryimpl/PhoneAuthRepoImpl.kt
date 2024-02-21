package com.example.auth.data.repositoryimpl

import com.core.data.remote.ApiKeys
import com.example.auth.data.model.ResOtp
import com.example.auth.data.remotesource.PhoneAuthApi
import com.example.auth.data.repository.PhoneAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 21/01/24.
 */

class PhoneAuthRepoImpl @Inject constructor(
    private val api: PhoneAuthApi
) : PhoneAuthRepository {

    override suspend fun sendCredentials(phone: String): Flow<ResOtp?> = flow {
        val res = api.sendOtp(ApiKeys.PHONE_AUTH_API_KEY, phone)
        if(res.isSuccessful) {
            emit(res.body())
        } else {
            emit(null)
        }
    }

    override suspend fun verifyCredentials(phone: String, otp: String): Flow<ResOtp?> = flow {
        val res = api.verifyOtp(ApiKeys.PHONE_AUTH_API_KEY, phone, otp)
        if(res.isSuccessful) {
            emit(res.body())
        } else {
            emit(null)
        }
    }
}