package com.example.goodnote

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import com.example.goodnote.ui.MainActivity
import androidx.test.rule.ActivityTestRule
import com.example.goodnote.ui.models.NoteModel
import com.example.goodnote.ui.noteList.NoteListRecyclerViewAdapter
import org.junit.Rule



@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class CreateNoteTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun herpDerp() {

        // is button displayed?
        onView(withId(R.id.fabAdd)).check(matches(isClickable())).check(matches(isDisplayed()))

        // click the button
        onView(withId(R.id.fabAdd)).perform(click())
        onView(withId(R.id.notes_details_title))
            .perform(click()) // editText field must first be clicked then type in text
            .perform(typeText("hey test 10"))
        // check they match unnecessarily
        // note that nothing else happened; we are staying on note details screen
        onView(withId(R.id.notes_details_title))
            .check(matches(withText("hey test 10")))

        pressBack() // --> to remove keyboard
        pressBack() // --> to go back to recycler view

        onView(withId(R.id.notes_list_recycler_view))
            .perform(RecyclerViewActions.actionOnItem<NoteListRecyclerViewAdapter.ViewHolder>(withText("hey test 10"), click()))

    }
}