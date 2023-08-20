package com.ev.greenh.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ev.greenh.R
import com.ev.greenh.auth.AuthActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.ev.greenh.auth.data.AuthRepository
import com.ev.greenh.auth.ui.composable.SignUpScreen
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before

/*
 * Created by Sudhanshu Kumar on 17/08/23.
 */

@RunWith(AndroidJUnit4::class)
class SignUpFragTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<AuthActivity>()

    @get:Rule
    val composeRule = createAndroidComposeRule<AuthActivity>()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SignUpViewModel

    private val repository = mockk<AuthRepository>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        viewModel = SignUpViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

//    @Test
//    fun randomTest() {
//        onView(withId(R.id.btntest)).perform(click())
//        onView(withId(R.id.btntest)).check(matches(withText("ChangedText")))
//    }

    @Test
    fun firstComposeTest() {
        composeRule.mainClock.autoAdvance = false
        val composeView = composeRule.activity.findViewById<ComposeView>(R.id.signUpFragComposeView)
        composeView.setContent {
            SignUpScreen(viewModel = viewModel)
        }
        composeRule.mainClock.advanceTimeBy(500L)
        composeRule.onNodeWithTag("testBtn").assertExists()

    }

}