package com.example.goodnote.ui

import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.goodnote.testScreen
import com.example.goodnote.TestScreenRobot
import com.example.goodnote.isScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class TestActivityTest{
    /**
     * Robot classes shouldn't be god classes (like TestScreenRobot is)
     * "Each screen has its own robot. We can use multiple robots in a test scenario."
     * example: testScreen {...}
     *
     */

    @get:Rule
    var activityRule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    val isScreenRobot = isScreen()

    val isDoingRobot = TestScreenRobot()

    @Test
    fun isScreen_Correct() = isScreenRobot.testScreen()

    @Test
    fun shouldShowImage_WhenButtonIsClicked_ThenHideImage_WhenButtonIsClickedAgain() {

        // needs to be kotlinized properly :)

        isScreenRobot.run {
            testScreen() // verify all is visible and correct
        }

        testScreen {
            clickBtn_Image() // do
            imageHidden_isVisible() // verify
            clickBtn_Image() // do
            imageHidden_isHidden() // verify
        }
    }
}