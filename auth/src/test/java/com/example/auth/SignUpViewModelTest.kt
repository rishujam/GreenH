package com.example.auth

import com.example.auth.domain.usecase.OtpSignUpUseCase
import com.example.auth.domain.usecase.SendOtpUseCase
import com.example.auth.test.fakes.PhoneAuthRepoFakeImpl
import com.example.auth.test.fakes.UserDataRepoFakeImpl
import com.example.auth.test.util.Constants
import com.example.auth.ui.SignUpViewModel
import com.example.auth.ui.events.SignUpEvents
import com.example.auth.ui.states.SignUpProgress
import com.example.auth.ui.states.SignUpState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/*
 * Created by Sudhanshu Kumar on 05/05/24.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {

    private lateinit var viewModel: SignUpViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        val phoneAuthRepo = PhoneAuthRepoFakeImpl()
        val userDataRepo = UserDataRepoFakeImpl()
        val sendOtpUseCase = SendOtpUseCase(phoneAuthRepo)
        val otpSignUpUseCase = OtpSignUpUseCase(phoneAuthRepo, userDataRepo)
        viewModel = SignUpViewModel(
            sendOtpUseCase = sendOtpUseCase,
            otpSignUpUseCase = otpSignUpUseCase,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `on event SendOtp on success change screen to VerifyPhoneStage` () = runTest {
        assert(viewModel.state.screen == SignUpProgress.EnterPhoneStage)
        viewModel.onEvent(SignUpEvents.SendOtp(Constants.SUCCESS_PHONE_NO))
        testDispatcher.scheduler.advanceUntilIdle()
        assert(viewModel.state.phoneNo == Constants.SUCCESS_PHONE_NO)
        assert(viewModel.state.screen == SignUpProgress.VerifyPhoneStage)
    }

    @Test
    fun `on event SendOtp on error update state to error` () = runTest {
        assert(viewModel.state.error == null)
        viewModel.onEvent(SignUpEvents.SendOtp(Constants.FAIL_PHONE_NO))
        testDispatcher.scheduler.advanceUntilIdle()
        assert(viewModel.state.phoneNo == Constants.FAIL_PHONE_NO)
        assert(viewModel.state.error == "unable to send otp")
    }

    @Test
    fun `on event Wrong number redirect back to enter phone screen` () {
        viewModel.state.phoneNo = Constants.EXISTING_USER_PHONE
        viewModel.state.screen = SignUpProgress.VerifyPhoneStage
        viewModel.onEvent(SignUpEvents.WrongNo)
        assert(viewModel.state.phoneNo == "" && viewModel.state.screen == SignUpProgress.EnterPhoneStage)
    }

    @Test
    fun `on event Verify click success update state to Verified Screen` () = runTest {
        //prepare
        viewModel.state.screen = SignUpProgress.VerifyPhoneStage
        viewModel.state.phoneNo = Constants.SUCCESS_PHONE_NO
        //action
        viewModel.onEvent(SignUpEvents.VerifyClick(Constants.VERIFY_OTP_SUCCESS, 15))
        testDispatcher.scheduler.advanceUntilIdle()
        //assert
        assert(viewModel.state.screen == SignUpProgress.VerifiedPhoneStage)
    }

    @Test
    fun `on event Verify click error update state to Verified Screen` () = runTest {
        //prepare
        viewModel.state.screen = SignUpProgress.VerifyPhoneStage
        viewModel.state.phoneNo = Constants.SUCCESS_PHONE_NO
        //action
        viewModel.onEvent(SignUpEvents.VerifyClick(Constants.VERIFY_OTP_INCORRECT, 15))
        testDispatcher.scheduler.advanceUntilIdle()
        //assert
        assert(viewModel.state.screen == SignUpProgress.VerifyPhoneStage)
        assert(viewModel.state.error?.isNotEmpty() == true)
    }

    @After
    fun tearDown() {
        viewModel.state = SignUpState()
    }

}