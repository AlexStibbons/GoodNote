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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class TestActivityTest{

    @get:Rule
    var activityRule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    @Test
    fun shouldShowImage_WhenButtonIsClicked_ThenHideImage_WhenButtonIsClickedAgain() {

        onView(withId(R.id.test_btn))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.test_image_hidden))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE))) // alternative to isDisplayed()

        onView(withId(R.id.test_btn))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.test_image_hidden))
            .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun customImageMatcher() {

        onView(withId(R.id.test_image))
            .check(matches(withImage(R.mipmap.ic_goodnote_launcher_round)))

    }
}