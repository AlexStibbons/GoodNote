package com.example.goodnote

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers


fun testScreen(smt: TestScreenRobot. () -> Unit) {
    TestScreenRobot().apply { smt() }
}


class TestScreenRobot {

    fun clickBtn_Image() {
        Espresso.onView(ViewMatchers.withId(R.id.test_btn))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())
    }

    fun imageHidden_isHidden() {
        Espresso.onView(ViewMatchers.withId(R.id.test_image_hidden))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
            .check(ViewAssertions.matches(CustomMatcher.withImage(R.mipmap.ic_goodnote_launcher_round)))
    }

    fun imageHidden_isVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.test_image_hidden))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(ViewAssertions.matches(CustomMatcher.withImage(R.mipmap.ic_goodnote_launcher_round)))

    }

    fun isCorrect() {
        Espresso.onView(ViewMatchers.withId(R.id.test_text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.test_image))
            .check(ViewAssertions.matches(CustomMatcher.withImage(R.mipmap.ic_goodnote_launcher_round)))
    }
}

