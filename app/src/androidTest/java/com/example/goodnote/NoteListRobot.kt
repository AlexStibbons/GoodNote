package com.example.goodnote

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.goodnote.ui.noteList.NoteListRecyclerViewAdapter

fun noteListScreen(func: NoteListRobot.() -> Unit) = NoteListRobot().apply(func)

class NoteListRobot {

    fun isCorrect() {

        onView(withId(R.id.fabAdd))
            .check(matches(ViewMatchers.isClickable()))
            .check(matches(isDisplayed()))

        onView(withId(R.id.notes_list_recycler_view))
            .check(matches(isDisplayed()))
    }

    fun clickNewNote() {
        onView(withId(R.id.fabAdd))
            .perform(ViewActions.click())
    }

    fun clickOnNoteWithTitle(title: String) {

    }

    fun clickItemOnPosition(position: Int) {
        onView(withId(R.id.notes_list_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<NoteListRecyclerViewAdapter.ViewHolder>(
                    position,
                    ViewActions.click()
                )
            )
    }
}