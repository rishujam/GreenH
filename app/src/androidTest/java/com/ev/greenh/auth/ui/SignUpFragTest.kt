package com.ev.greenh.auth.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ev.greenh.R
import com.ev.greenh.auth.AuthActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.assertion.ViewAssertions.matches

/*
 * Created by Sudhanshu Kumar on 17/08/23.
 */

@RunWith(AndroidJUnit4::class)
class SignUpFragTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<AuthActivity>()

    @get:Rule
    val composeRule = createAndroidComposeRule<AuthActivity>()

    @Test
    fun randomTest() {
        onView(withId(R.id.btntest)).perform(click())

        onView(withId(R.id.btntest)).check(matches(withText("ChangedText")))
    }

}