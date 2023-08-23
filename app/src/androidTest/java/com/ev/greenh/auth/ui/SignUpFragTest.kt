package com.ev.greenh.auth.ui

import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ev.greenh.R
import com.ev.greenh.auth.AuthActivity
import com.ev.greenh.auth.data.AuthRepository
import com.ev.greenh.auth.ui.composable.SignUpScreen
import com.example.testing.Tags
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable
import com.google.firebase.auth.PhoneAuthProvider
import io.mockk.coEvery
import io.mockk.every
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

    companion object {
        private const val TOKEN_TEST = "token_test"
        private const val VERIFICATION_ID_TEST = "verification_id_test"
    }

    @get:Rule
    val composeRule = createAndroidComposeRule<AuthActivity>()

    private lateinit var viewModel: SignUpViewModel

    private val repository = mockk<AuthRepository>()

    private lateinit var token: AbstractSafeParcelable

    @Before
    fun setup() {
        viewModel = SignUpViewModel(repository)
        composeRule.mainClock.autoAdvance = false
        val composeView = composeRule.activity.findViewById<ComposeView>(R.id.signUpFragComposeView)
        composeView.setContent {
            SignUpScreen(viewModel = viewModel)
        }
        composeRule.mainClock.advanceTimeBy(400L)
        composeRule.mainClock.autoAdvance = true
    }

//    @Test
//    fun randomTest() {
//        onView(withId(R.id.btntest)).perform(click())
//        onView(withId(R.id.btntest)).check(matches(withText("ChangedText")))
//    }

    @Test
    fun brandingViewVisible_onSignUpOpen() {
        composeRule.onNodeWithTag(Tags.BRAND_DESCRIPTION).assertExists()
        composeRule.onNodeWithContentDescription(Tags.BRAND_LOGO).assertExists()
        composeRule.onNodeWithTag(Tags.BRAND_NAME).assertExists()
    }

    @Test
    fun phoneViewVisible_onSignUpOpen() {
        composeRule.onNodeWithTag(Tags.PHONE_ENTER).assertExists()
        composeRule.onNodeWithTag(Tags.PHONE_VIEW_NEXT_BTN).assertExists()
    }

    @Test
    fun verifyViewNotVisible_onSignUpOpen() {
        composeRule.onNodeWithTag(Tags.VERIFY_ENTER_OTP).assertDoesNotExist()
        composeRule.onNodeWithTag(Tags.RESEND_OTP).assertDoesNotExist()
    }

    @Test
    fun validNumberNextClick_redirectToEnterOtp() {
//        coEvery {
//            repository.sendOtp(null)
//        } returns viewModel.callbacks.onCodeSent(VERIFICATION_ID_TEST, token)

        val phoneView = composeRule.onNodeWithTag(testTag = Tags.PHONE_ENTER)
        phoneView.performTextInput("9999999999")
        phoneView.performImeAction()
        composeRule.onNodeWithTag(Tags.PHONE_VIEW_NEXT_BTN).performClick()

    }

}