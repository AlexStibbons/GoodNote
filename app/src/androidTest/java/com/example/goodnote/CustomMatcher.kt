package com.example.goodnote

import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.example.goodnote.ui.noteList.NoteListRecyclerViewAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

// this whole things is incorrect
class CustomMatcher {

    companion object {

        fun withNoteTitle(expected: String): Matcher<NoteListRecyclerViewAdapter.ViewHolder> {
            return object : TypeSafeMatcher<NoteListRecyclerViewAdapter.ViewHolder>() {

                override fun describeTo(description: Description?) {
                    description?.appendText("No such thing as $expected found")
                }

                override fun matchesSafely(item: NoteListRecyclerViewAdapter.ViewHolder?): Boolean {

                   val isit =  item?.let {
                        it.getTitle().equals(expected)
                    }

                    return isit!!
                }
            }
        }

    }
}