package com.example.auth.test.fakes

import com.core.util.Constants
import com.example.auth.data.model.ResOtp
import com.example.auth.data.repository.PhoneAuthRepository
import com.example.auth.test.util.AuthConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/*
 * Created by Sudhanshu Kumar on 17/02/24.
 */

class PhoneAuthRepoFakeImpl : PhoneAuthRepository {

    override suspend fun sendCredentials(phone: String): Flow<ResOtp?> = flow {
        when (phone) {
            AuthConstants.SUCCESS_PHONE_NO -> {
                emit(
                    ResOtp(
                        AuthConstants.RANDOM_STRING,
                        Constants.Other.SUCCESS_STRING
                    )
                )
            }

            AuthConstants.FAIL_PHONE_NO -> {
                emit(
                    ResOtp(
                        Constants.Other.EMPTY_STRING,
                        Constants.Other.ERROR_STRING
                    )
                )
            }

            AuthConstants.SEND_OTP_NULL_PHONE_NO -> emit(null)
        }
    }

    override suspend fun verifyCredentials(phone: String, otp: String): Flow<ResOtp?> = flow {
        when(otp) {
            AuthConstants.VERIFY_OTP_INCORRECT -> {
                emit(
                    ResOtp(
                        Constants.Other.EMPTY_STRING,
                        Constants.Other.ERROR_STRING
                    )
                )
            }

            AuthConstants.VERIFY_OTP_SUCCESS -> {
                emit(
                    ResOtp(
                        AuthConstants.RANDOM_STRING,
                        Constants.Other.SUCCESS_STRING
                    )
                )
            }

            AuthConstants.VERIFY_OTP_NULL_RES -> {
                emit(null)
            }
        }
    }
}