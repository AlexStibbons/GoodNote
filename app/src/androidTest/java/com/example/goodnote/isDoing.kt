package com.example.goodnote

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

class isDoing {

    fun testScreen_clickBtn_Image(){
        Espresso.onView(ViewMatchers.withId(R.id.test_btn))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(ViewActions.click())
    }

    fun testScreen_ImageHidden_isHidden() {
        Espresso.onView(ViewMatchers.withId(R.id.test_image_hidden))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
            .check(ViewAssertions.matches(CustomMatcher.withImage(R.mipmap.ic_goodnote_launcher_round)))
    }

    fun testScreen_ImageHidden_isVisible() {
        Espresso.onView(ViewMatchers.withId(R.id.test_image_hidden))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(ViewAssertions.matches(CustomMatcher.withImage(R.mipmap.ic_goodnote_launcher_round)))

    }

    fun lil(smt: () -> Unit) {
        smt()
    }

}

fun testScreen(smt: isDoing. () -> Unit) {
    isDoing().apply { smt() }
}