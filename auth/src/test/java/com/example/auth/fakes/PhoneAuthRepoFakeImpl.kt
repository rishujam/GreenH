package com.example.auth.fakes

import com.example.auth.data.model.ResOtp
import com.example.auth.data.repository.PhoneAuthRepository
import com.example.auth.domain.testutils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
 * Created by Sudhanshu Kumar on 17/02/24.
 */

class PhoneAuthRepoFakeImpl : PhoneAuthRepository {

    override suspend fun sendCredentials(phone: String): Flow<ResOtp?> = flow {
        if (phone == Constants.SEND_OTP_SUCCESS_PHONE_NO) {
            emit(
                ResOtp(
                    Constants.RANDOM_STRING,
                    com.core.data.Constants.Other.SUCCESS_STRING
                )
            )
        } else if (phone == Constants.SEND_OTP_FAIL_PHONE_NO) {
            emit(
                ResOtp(
                    com.core.data.Constants.Other.EMPTY_STRING,
                    com.core.data.Constants.Other.ERROR_STRING
                )
            )
        }
    }

    override suspend fun verifyCredentials(phone: String, otp: String): Flow<ResOtp?> {
        TODO("Not yet implemented")
    }
}