package com.example.goodnote

import android.view.View
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
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class CreateNoteTest {

    private val SOME_TEXT = "some text"

    @get:Rule // or use ActivityScenario, which is apparently standard for Espresso 3 [?]
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun herpDerp() {

        // is button displayed?
        onView(withId(R.id.fabAdd)).check(matches(isClickable())).check(matches(isDisplayed()))

        // click the button
        onView(withId(R.id.fabAdd)).perform(click())
        onView(withId(R.id.notes_details_title))
            .perform(click()) // should be just .perform(type(), closeSoftKeyboard()) but that's not working
            .perform(typeText(SOME_TEXT))
        // check they match unnecessarily
        // note that nothing else happened; we are staying on note details screen
        onView(withId(R.id.notes_details_title))
            .check(matches(withText(SOME_TEXT)))

        pressBack() // --> to remove keyboard
        pressBack() // --> to go back to recycler view

        // we go back to recycler view;
        onView(withId(R.id.notes_list_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<NoteListRecyclerViewAdapter.ViewHolder>(
                    1,
                    click()
                )
            )
        // item with text didn't really work

        // now we're in note details screen again
        onView(withId(R.id.notes_details_title))
            .check(matches(withText("note two")))
        // still inside the details of note 2
        onView(withId(R.id.notes_details_text))
            .perform(click())
            .perform(typeText(SOME_TEXT))

        // going back to recycler view
        pressBack()
        pressBack()
    }

    @Test
    fun herpDerp2() {
        val TEXT_AGAIN = "this that and"
        // click the button
        onView(withId(R.id.fabAdd)).perform(click())
        onView(withId(R.id.notes_details_title))
            .perform(click())
            .perform(typeText(TEXT_AGAIN))
        //  .perform(closeSoftKeyboard()) --> this won't work even though it's in docs??

        // check they match unnecessarily
        // note that nothing else happened; we are staying on note details screen
        onView(withId(R.id.notes_details_title))
            .check(matches(withText(TEXT_AGAIN)))
        pressBack() // --> close keyboard
        pressBack() // --> to go back to recycler view

        // scroll down to new item
        // scrollToHolder apparently needs a custom matcher? or not??
    }

    @Test
    fun recyclerView() {
        // clicks using position
        onView(withId(R.id.notes_list_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<NoteListRecyclerViewAdapter.ViewHolder>(7, click()))
        pressBack()

        // scrollTo using descendants works, without descendants doesn't work
        onView(withId(R.id.notes_list_recycler_view))
            .perform(RecyclerViewActions.scrollTo<NoteListRecyclerViewAdapter.ViewHolder>
                (hasDescendant(withText("scroll 5"))))
            .perform(click()) // does NOT click on scroll 5
        pressBack()

/*     custom matcher issue
       onView(withId(R.id.notes_list_recycler_view))
            .perform(RecyclerViewActions.actionOnHolderItem<NoteListRecyclerViewAdapter.ViewHolder>(
                withNoteTitle("scroll 5"), click()))
        pressBack()*/
    }
}