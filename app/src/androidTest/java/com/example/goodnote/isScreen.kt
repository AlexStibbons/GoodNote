package com.example.goodnote

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

class isScreen {

    fun noteListScreen() {
        onView(withId(R.id.notes_list_recycler_view)).check(matches(isDisplayed()))
    }

    fun testScreen() {
        onView(withId(R.id.test_text))
            .check(matches(isDisplayed()))

        onView(withId(R.id.test_image))
            .check(matches(CustomMatcher.withImage(R.mipmap.ic_goodnote_launcher_round)))
    }
}