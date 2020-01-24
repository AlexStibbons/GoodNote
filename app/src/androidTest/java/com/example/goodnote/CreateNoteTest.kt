package com.example.goodnote

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.goodnote.CustomMatcher.Companion.withNoteTitle
import com.example.goodnote.ui.MainActivity
import com.example.goodnote.ui.noteList.NoteListRecyclerViewAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class CreateNoteTest {

    private val SOME_TEXT = "some text"

    // more on ActivityScenario and why to use it:
    // https://medium.com/stepstone-tech/better-tests-with-androidxs-activityscenario-in-kotlin-part-1-6a6376b713ea
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun createNote() {

        noteListScreen {
            isCorrect()
            clickNewNote()
        }

        noteDetailsScreen {
            changeNoteTitle(SOME_TEXT)
            checkTitleMatches(SOME_TEXT)
            chageNoteText(SOME_TEXT)
            pressBack2() // goto list
        }
    }

    @Test
    fun createNote_OLD() {
        onView(withId(R.id.fabAdd)).perform(click())

        onView(withId(R.id.notes_details_title))
            .perform(click())
            .perform(typeText("text text text"))
        pressBack()
        pressBack()
    }

    @Test
    fun herpDerp2() {
        val TEXT_AGAIN = "this that and"
        // click the button
        noteListScreen {
            clickNewNote()
        }

        noteDetailsScreen {
            changeNoteTitle("title")
        }
    }



    @Test
    fun recyclerView() {
        // clicks using position
        onView(withId(R.id.notes_list_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<NoteListRecyclerViewAdapter.ViewHolder>(1, click()))
        pressBack()

        // scrollTo using descendants works, without descendants doesn't work
        onView(withId(R.id.notes_list_recycler_view))
            .perform(RecyclerViewActions.scrollTo<NoteListRecyclerViewAdapter.ViewHolder>
                (hasDescendant(withText(SOME_TEXT))))
            .perform(click()) // does NOT click on scroll 5
        pressBack()

        // scroll to with custom matcher
        onView(withId(R.id.notes_list_recycler_view))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(withNoteTitle(SOME_TEXT)))

        // click with custom matcher
        onView(withId(R.id.notes_list_recycler_view))
            .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(withNoteTitle(SOME_TEXT), click()))
    }
}