package com.example.auth.domain.usecase

import com.core.util.Resource
import com.example.auth.data.model.ResOtp
import com.example.auth.domain.testutils.Constants
import com.example.auth.fakes.PhoneAuthRepoFakeImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        val events = mutableListOf<Resource<Boolean>>()
        sendOtpUseCase.invoke(Constants.SEND_OTP_FAIL_PHONE_NO).collect {
            events.add(it)
        }
        val firstEmission = events.getOrNull(0)
        val secondEmission =  events.getOrNull(1)
        if(firstEmission is Resource.Loading && secondEmission is Resource.Error) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun `On null response emit error` () = runTest {
        val events = mutableListOf<Resource<Boolean>>()
        sendOtpUseCase.invoke(Constants.SEND_OTP_FAIL_PHONE_NO).collect {
            events.add(it)
        }
        val firstEmission = events.getOrNull(0)
        val secondEmission =  events.getOrNull(1)
        if(firstEmission is Resource.Loading && secondEmission is Resource.Error) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun `On success emit success` () = runTest {
        val events = mutableListOf<Resource<Boolean>>()
        sendOtpUseCase.invoke(Constants.SEND_OTP_SUCCESS_PHONE_NO).collect {
            events.add(it)
        }
        val firstEmission = events.getOrNull(0)
        val secondEmission =  events.getOrNull(1)
        if(firstEmission is Resource.Loading && secondEmission is Resource.Success) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun `On Invalid number emit error` () = runTest {
        val events = mutableListOf<Resource<Boolean>>()
        sendOtpUseCase.invoke(Constants.SEND_OTP_WRONG_PHONE_NO).collect {
            events.add(it)
        }
        if(events.size == 1 && events[0] is Resource.Error) {
            assert(true)
        } else {
            assert(false)
        }
    }

}