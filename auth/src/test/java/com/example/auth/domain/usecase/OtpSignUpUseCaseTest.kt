package com.example.auth.domain.usecase

import com.core.util.Resource
import com.example.auth.domain.model.SignUpComplete
import com.example.auth.domain.testutils.Constants
import com.example.auth.fakes.PhoneAuthRepoFakeImpl
import com.example.auth.fakes.UserDataRepoFakeImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

/*
 * Created by Sudhanshu Kumar on 22/02/24.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class OtpSignUpUseCaseTest {

    private val fakePhoneAuthRepo = PhoneAuthRepoFakeImpl()
    private val fakeUserDataRepo = UserDataRepoFakeImpl()
    private val otpSignUpUseCase = OtpSignUpUseCase(fakePhoneAuthRepo, fakeUserDataRepo)

    @Test
    fun `When incomplete otp entered emit error` () = runTest {
        val events = mutableListOf<Resource<SignUpComplete>>()
        otpSignUpUseCase.invoke(
            Constants.SEND_OTP_SUCCESS_PHONE_NO,
            Constants.VERIFY_OTP_INCOMPLETE,
            Constants.TEST_BUILD_VERSION
        ).collect {
            events.add(it)
        }
        if(events.size == 1 && events[0] is Resource.Error) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun `When correct otp entered and user already exist ` () = runTest {
        val events = mutableListOf<Resource<SignUpComplete>>()
        otpSignUpUseCase.invoke(
            Constants.EXISTING_USER_PHONE,
            Constants.VERIFY_OTP_SUCCESS,
            Constants.TEST_BUILD_VERSION
        ).collect {
            events.add(it)
        }
        val firstEmission = events.getOrNull(0)
        val secondEmission = events.getOrNull(1)
        val oldUser = if(secondEmission is Resource.Success) {
            secondEmission.data?.newUser == false
        } else false
        if (
            events.size == 2 &&
            firstEmission is Resource.Loading &&
            oldUser
        ) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun `When correct otp entered and user is new ` () = runTest {
        val events = mutableListOf<Resource<SignUpComplete>>()
        otpSignUpUseCase.invoke(
            Constants.NEW_USER,
            Constants.VERIFY_OTP_SUCCESS,
            Constants.TEST_BUILD_VERSION
        ).collect {
            events.add(it)
        }
        val firstEmission = events.getOrNull(0)
        val secondEmission = events.getOrNull(1)
        val profileSyncedOnServer = if (secondEmission is Resource.Success) {
            secondEmission.data?.savedUserDataOnServer == true
        } else false
        if (
            events.size == 2 &&
            firstEmission is Resource.Loading &&
            profileSyncedOnServer
        ) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun `on incorrect otp entered emit error` () = runTest {
        val events = mutableListOf<Resource<SignUpComplete>>()
        otpSignUpUseCase.invoke(
            Constants.EXISTING_USER_PHONE,
            Constants.VERIFY_OTP_INCORRECT,
            Constants.TEST_BUILD_VERSION
        ).collect {
            events.add(it)
        }
        val firstEmission = events.getOrNull(0)
        val secondEmission = events.getOrNull(1)
        if(events.size == 2 && firstEmission is Resource.Loading && secondEmission is Resource.Error) {
            assert(true)
        } else {
            assert(false)
        }
    }

    @Test
    fun `on null res emit error` () = runTest {
        val events = mutableListOf<Resource<SignUpComplete>>()
        otpSignUpUseCase.invoke(
            Constants.EXISTING_USER_PHONE,
            Constants.VERIFY_OTP_NULL_RES,
            Constants.TEST_BUILD_VERSION
        ).collect {
            events.add(it)
        }
        val firstEmission = events.getOrNull(0)
        val secondEmission = events.getOrNull(1)
        if(events.size == 2 && firstEmission is Resource.Loading && secondEmission is Resource.Error) {
            assert(true)
        } else {
            assert(false)
        }
    }

}