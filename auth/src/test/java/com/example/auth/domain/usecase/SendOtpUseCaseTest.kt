package com.example.auth.domain.usecase

import app.cash.turbine.test
import com.core.util.Resource
import com.example.auth.data.model.ResOtp
import com.example.auth.domain.testutils.Constants
import com.example.auth.fakes.PhoneAuthRepoFakeImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Test

/*
 * Created by Sudhanshu Kumar on 17/02/24.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class SendOtpUseCaseTest {

    private val phoneAuthRepo = PhoneAuthRepoFakeImpl()
    private val sendOtpUseCase = SendOtpUseCase(phoneAuthRepo)

    @Test
    fun `On failure emit error` () = runTest {
        sendOtpUseCase.invoke(Constants.SEND_OTP_FAIL_PHONE_NO).test {
            val firstEmission = awaitItem()
            val secondEmission = awaitItem()
            awaitComplete()
            if(firstEmission is Resource.Loading && secondEmission is Resource.Error) {
                assert(true)
            } else {
                assert(false)
            }
        }
    }

    @Test
    fun `On null response emit error` () = runTest {
        sendOtpUseCase.invoke(Constants.SEND_OTP_NULL_PHONE_NO).test {
            val firstEmission = awaitItem()
            val secondEmission = awaitItem()
            awaitComplete()
            if(firstEmission is Resource.Loading && secondEmission is Resource.Error) {
                assert(true)
            } else {
                assert(false)
            }
        }
    }

    @Test
    fun `On success emit success` () = runTest {
        sendOtpUseCase.invoke(Constants.SEND_OTP_SUCCESS_PHONE_NO).test {
            val firstEmission = awaitItem()
            val secondEmission = awaitItem()
            awaitComplete()
            if(firstEmission is Resource.Loading && secondEmission is Resource.Success) {
                assert(true)
            } else {
                assert(false)
            }
        }
    }

    @Test
    fun `On Invalid number emit error` () = runTest {
        sendOtpUseCase.invoke(Constants.SEND_OTP_WRONG_PHONE_NO).test {
            val firstEmission = awaitItem()
            awaitComplete()
            if(firstEmission is Resource.Error) {
                assert(true)
            } else{
                assert(false)
            }
        }
    }

}