package com.example.auth.data.repositoryimpl

import com.core.util.Constants
import com.example.auth.data.model.ResOtp
import com.example.auth.data.repository.PhoneAuthRepository
import com.example.auth.test.util.AuthConstants
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
        if(phone == AuthConstants.SUCCESS_PHONE_NO || phone == AuthConstants.SUCCESS_PHONE_NO_2) {
            emit(
                ResOtp(
                    AuthConstants.RANDOM_STRING,
                    Constants.Other.SUCCESS_STRING
                )
            )
        } else {
            emit(
                ResOtp(
                    Constants.Other.EMPTY_STRING,
                    Constants.Other.ERROR_STRING
                )
            )
        }
    }

    override suspend fun verifyCredentials(phone: String, otp: String): Flow<ResOtp?> = flow {
        delay(1000L)
        if(otp == AuthConstants.VERIFY_OTP_SUCCESS) {
            emit(
                ResOtp(
                    AuthConstants.RANDOM_STRING,
                    Constants.Other.SUCCESS_STRING
                )
            )
        } else {
            emit(
                ResOtp(
                    Constants.Other.EMPTY_STRING,
                    Constants.Other.ERROR_STRING
                )
            )
        }
    }

}