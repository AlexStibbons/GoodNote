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
    fun herpDerp2() {
        noteListScreen {
            clickNewNote()
        }

        noteDetailsScreen {
            changeNoteTitle("title")
        }
    }



    @Test
    fun recyclerView() {

       noteListScreen {
           clickOnItemOnPosition(1)
       }

        noteDetailsScreen {
            pressBack2()
        }

        noteListScreen {
            scrollToItemWithTitle("title")
            clickOnItemWithTitle("title")
        }

        noteDetailsScreen {
            pressBack2()
        }
    }
}