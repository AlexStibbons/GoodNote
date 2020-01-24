package com.example.goodnote

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

fun noteDetailsScreen(func: NoteDetailsRobot.() -> Unit) = NoteDetailsRobot().apply {func}

class NoteDetailsRobot {

    fun changeNoteTitle(title: String) {
        Espresso.onView(ViewMatchers.withId(R.id.notes_details_title))
            .perform(ViewActions.click())
            .perform(ViewActions.typeText(title))
        ViewActions.pressBack()
    }

    fun checkTitleMatches(title: String) {
        Espresso.onView(ViewMatchers.withId(R.id.notes_details_title))
            .check(ViewAssertions.matches(ViewMatchers.withText(title)))
    }

    fun chageNoteText(text: String) {
        Espresso.onView(ViewMatchers.withId(R.id.notes_details_text))
            .perform(ViewActions.click())
            .perform(ViewActions.typeText(text))
    }

    fun pressBack2() {
        ViewActions.pressBack()
    }

}