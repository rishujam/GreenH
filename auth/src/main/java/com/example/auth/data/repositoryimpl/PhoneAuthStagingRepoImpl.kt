package com.example.auth.data.repositoryimpl

import com.example.auth.data.model.ResOtp
import com.example.auth.data.repository.PhoneAuthRepository
import com.example.auth.test.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 24/10/24.
 */

class PhoneAuthStagingRepoImpl @Inject constructor() : PhoneAuthRepository {

    override suspend fun sendCredentials(phone: String): Flow<ResOtp?> = flow {
        delay(1000L)
        if(phone == Constants.SUCCESS_PHONE_NO) {
            emit(
                ResOtp(
                    Constants.RANDOM_STRING,
                    com.core.data.Constants.Other.SUCCESS_STRING
                )
            )
        } else {
            emit(
                ResOtp(
                    com.core.data.Constants.Other.EMPTY_STRING,
                    com.core.data.Constants.Other.ERROR_STRING
                )
            )
        }
    }

    override suspend fun verifyCredentials(phone: String, otp: String): Flow<ResOtp?> = flow {
        delay(1000L)
        if(otp == Constants.VERIFY_OTP_SUCCESS) {
            emit(
                ResOtp(
                    Constants.RANDOM_STRING,
                    com.core.data.Constants.Other.SUCCESS_STRING
                )
            )
        } else {
            emit(
                ResOtp(
                    com.core.data.Constants.Other.EMPTY_STRING,
                    com.core.data.Constants.Other.ERROR_STRING
                )
            )
        }

    }

}