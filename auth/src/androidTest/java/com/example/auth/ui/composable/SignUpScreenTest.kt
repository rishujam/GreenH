package com.example.auth.ui.composable

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.auth.MainCoroutineRule
import com.example.auth.domain.usecase.OtpSignUpUseCase
import com.example.auth.domain.usecase.SendOtpUseCase
import com.example.auth.runBlockingTest
import com.example.auth.test.fakes.PhoneAuthRepoFakeImpl
import com.example.auth.test.fakes.UserDataRepoFakeImpl
import com.example.auth.test.util.Constants
import com.example.auth.ui.SignUpViewModel
import com.example.auth.ui.states.SignUpProgress
import com.example.auth.ui.states.SignUpState
import com.example.testing.Tags
import dagger.hilt.android.testing.HiltAndroidRule
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/*
 * Created by Sudhanshu Kumar on 03/03/24.
 */

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SignUpScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val coroutineRule = MainCoroutineRule()

    @Test
    fun visibility_enter_phone_state () {
        val state = SignUpState(screen = SignUpProgress.EnterPhoneStage)
        val phoneAuthRepo = PhoneAuthRepoFakeImpl()
        val userDataRepo = UserDataRepoFakeImpl()
        val sendOtpUseCase = SendOtpUseCase(phoneAuthRepo)
        val otpSignUpUseCase = OtpSignUpUseCase(phoneAuthRepo, userDataRepo)
        val viewModel = SignUpViewModel(sendOtpUseCase, otpSignUpUseCase)
        composeRule.apply {
            setContent {
                SignUpScreen(state, viewModel::onEvent, 1) {    }
            }
            onNodeWithTag(Tags.PHONE_ENTER).assertIsDisplayed()
            onNodeWithTag(Tags.PHONE_VIEW_NEXT_BTN).assertIsDisplayed()
            onNodeWithTag(Tags.OTP_ENTER_VIEW).assertDoesNotExist()
            onNodeWithTag(Tags.VERIFY_BTN).assertDoesNotExist()
        }
    }

    @Test
    fun visibility_otp_state () {
        val state = SignUpState(screen = SignUpProgress.VerifyPhoneStage)
        val phoneAuthRepo = PhoneAuthRepoFakeImpl()
        val userDataRepo = UserDataRepoFakeImpl()
        val sendOtpUseCase = SendOtpUseCase(phoneAuthRepo)
        val otpSignUpUseCase = OtpSignUpUseCase(phoneAuthRepo, userDataRepo)
        val viewModel = SignUpViewModel(sendOtpUseCase, otpSignUpUseCase)
        composeRule.apply {
            setContent {
                SignUpScreen(state = state, onEvent = viewModel::onEvent, buildVersion = 1) {   }
            }
            onNodeWithTag(Tags.OTP_ENTER_VIEW).assertIsDisplayed()
            onNodeWithTag(Tags.VERIFY_BTN).assertIsDisplayed()
            onNodeWithTag(Tags.PHONE_ENTER).assertDoesNotExist()
            onNodeWithTag(Tags.PHONE_VIEW_NEXT_BTN).assertDoesNotExist()
        }
    }

    @Test
    fun valid_phone_entered_navigate_to_enter_otp_screen() = coroutineRule.runBlockingTest {
        val phoneAuthRepo = PhoneAuthRepoFakeImpl()
        val userDataRepo = UserDataRepoFakeImpl()
        val sendOtpUseCase = SendOtpUseCase(phoneAuthRepo)
        val otpSignUpUseCase = OtpSignUpUseCase(phoneAuthRepo, userDataRepo)
        val viewModel = SignUpViewModel(sendOtpUseCase, otpSignUpUseCase)

        composeRule.apply {
            setContent {
                SignUpScreen(state = viewModel.state, onEvent = viewModel::onEvent, buildVersion = 1) {   }
            }
            onNodeWithTag(testTag = Tags.PHONE_ENTER).performTextInput(Constants.SUCCESS_PHONE_NO)
            onNodeWithTag(testTag = Tags.PHONE_ENTER).performImeAction()
            onNodeWithTag(Tags.PHONE_VIEW_NEXT_BTN).performClick()
            waitForIdle()
            composeRule.waitUntil(timeoutMillis = 2000) {
                viewModel.state.screen == SignUpProgress.VerifyPhoneStage
            }
            composeRule.onRoot().printToLog("UI_Hierarchy")
            onNodeWithText(Tags.VERIFY_ENTER_OTP).assertIsDisplayed()
        }
    }

}