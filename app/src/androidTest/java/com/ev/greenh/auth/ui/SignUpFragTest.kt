package com.ev.greenh.auth.ui

import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ev.greenh.R
import com.ev.greenh.auth.AuthActivity
import com.ev.greenh.auth.data.AuthRepository
import com.ev.greenh.auth.ui.composable.SignUpScreen
import com.ev.greenh.auth.ui.states.SignUpProgress
import com.ev.greenh.ui.MainActivity
import com.example.testing.Tags
import com.example.testing.TestConstants
import com.google.firebase.auth.PhoneAuthProvider
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
 * Created by Sudhanshu Kumar on 17/08/23.
 */

@RunWith(AndroidJUnit4::class)
class SignUpFragTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<AuthActivity>()

    @get:Rule
    val composeRule = createAndroidComposeRule<AuthActivity>()

    private lateinit var viewModel: SignUpViewModel

    private val repository = mockk<AuthRepository>()

    private val resendToken = mockk<PhoneAuthProvider.ForceResendingToken>()

    @Before
    fun setup() {
        viewModel = SignUpViewModel(repository)
        val composeView = composeRule.activity.findViewById<ComposeView>(R.id.signUpFragComposeView)
        composeView.setContent {
            SignUpScreen(viewModel = viewModel)
        }
        coEvery {
            repository.sendOtp(null)
        } returns viewModel.callbacks.onCodeSent(TestConstants.VERIFICATION_ID_TEST, resendToken)
        coEvery {
            repository.verifyUser(
                TestConstants.UI_TEST_OTP,
                TestConstants.VERIFICATION_ID_TEST,
                TestConstants.UI_TEST_NUMBER,
                composeRule.activity.applicationContext.getString(R.string.user_ref),
                composeRule.activity.applicationContext.getString(R.string.token),
            )
        } returns true
    }

//    @Test
//    fun randomTest() {
//        onView(withId(R.id.btntest)).perform(click())
//        onView(withId(R.id.btntest)).check(matches(withText("ChangedText")))
//    }

    @Test
    fun brandingViewVisible_onSignUpOpen() {
        composeRule.onNodeWithTag(Tags.BRAND_DESCRIPTION).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(Tags.BRAND_LOGO).assertIsDisplayed()
        composeRule.onNodeWithTag(Tags.BRAND_NAME).assertIsDisplayed()
    }

    @Test
    fun phoneViewVisible_onSignUpOpen() {
        composeRule.onNodeWithTag(Tags.PHONE_ENTER).assertIsDisplayed()
        composeRule.onNodeWithTag(Tags.PHONE_VIEW_NEXT_BTN).assertIsDisplayed()
    }

    @Test
    fun verifyViewNotVisible_onSignUpOpen() {
        composeRule.onNodeWithTag(Tags.VERIFY_ENTER_OTP).assertIsNotDisplayed()
        composeRule.onNodeWithTag(Tags.RESEND_OTP).assertIsNotDisplayed()
    }

    @Test
    fun validNumberNextClick_redirectToEnterOtp() {
        val phoneView = composeRule.onNodeWithTag(testTag = Tags.PHONE_ENTER)
        phoneView.performTextInput(TestConstants.UI_TEST_NUMBER)
        phoneView.performImeAction()
        composeRule.onNodeWithTag(Tags.PHONE_VIEW_NEXT_BTN).performClick()
        composeRule.onNodeWithTag(Tags.VERIFY_ENTER_OTP).assertIsDisplayed()
    }

    @Test
    fun validNumberValidOtp_redirectToMainActivity() {
        val phoneView = composeRule.onNodeWithTag(testTag = Tags.PHONE_ENTER)
        phoneView.performTextInput(TestConstants.UI_TEST_NUMBER)
        phoneView.performImeAction()
        composeRule.onNodeWithTag(Tags.PHONE_VIEW_NEXT_BTN).performClick()
        composeRule.onNodeWithTag(Tags.OTP_ENTER_VIEW).performTextInput(TestConstants.UI_TEST_OTP)
        composeRule.onNodeWithTag(Tags.VERIFY_BTN).performClick()
        composeRule.mainClock.autoAdvance = false
        composeRule.mainClock.advanceTimeBy(200L)
        composeRule.mainClock.autoAdvance = true
        if(viewModel.state.screen == SignUpProgress.VerifiedPhoneStage) {
            val mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
            onView(withId(R.id.bottomNavigationView)).check(matches(isDisplayed()))
            mainActivityScenario.close()
        } else {
            assert(false)
        }
    }

}