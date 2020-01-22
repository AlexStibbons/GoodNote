package com.example.goodnote.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.goodnote.R
import kotlinx.android.synthetic.main.activity_test.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TestActivityTest{

    @get:Rule
    var activityRule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    @Test
    fun shouldShowImage_WhenButtonIsClicked_ThenHideImage_WhenButtonIsClickedAgain() {

        onView(withId(R.id.test_btn))
            .check(matches(isDisplayed()))
            .perform(click())
            .perform(click())
    }


}