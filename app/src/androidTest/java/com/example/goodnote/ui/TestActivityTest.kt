package com.example.goodnote.ui

import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.goodnote.testScreen
import com.example.goodnote.isDoing
import com.example.goodnote.isScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class TestActivityTest{
    /**
     * Robot classes shouldn't be god classes (like isDoing is)
     * "Each screen has its own robot. We can use multiple robots in a test scenario."
     */

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
            testScreen() // verify all is visible and correct
        }
        
        testScreen {
            testScreen_clickBtn_Image()
            testScreen_ImageHidden_isVisible()
            testScreen_clickBtn_Image()
            testScreen_ImageHidden_isHidden()
        }
    }
}