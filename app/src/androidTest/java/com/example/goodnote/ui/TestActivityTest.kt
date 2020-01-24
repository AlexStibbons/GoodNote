package com.example.goodnote.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.goodnote.CustomMatcher.Companion.withImage
import com.example.goodnote.R
import com.example.goodnote.isDoing
import com.example.goodnote.isScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class TestActivityTest{

    @get:Rule
    var activityRule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    val isScreenRobot = isScreen()

    val isDoingRobot = isDoing()

    @Test
    fun isScreen_Correct() = isScreenRobot.testScreen()

    @Test
    fun shouldShowImage_WhenButtonIsClicked_ThenHideImage_WhenButtonIsClickedAgain() {

        // needs to be kotlinized properly :)

        isScreenRobot.run {
            testScreen()
        }

        isDoingRobot.run {
            testScreen_clickBtn_Image()
            testScreen_Image_isVisible()
            testScreen_clickBtn_Image()
            testScreen_Image_isHidden()
        }

    }


}