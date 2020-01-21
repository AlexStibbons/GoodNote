package com.example.goodnote

import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class CustomMatcher {

    companion object {

        fun withNoteTitle(expected: String): Matcher<View> {
            return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

                override fun matchesSafely(item: RecyclerView?): Boolean {
                    val noteTitle: TextView? = item?.findViewById(R.id.item_note_title)
                    return noteTitle!!.text!!.equals(expected)
                }

                override fun describeMismatch(item: Any?, description: Description?) {
                    super.describeMismatch(item, description)
                }

                override fun describeTo(description: Description?) {
                    description?.appendText("No such thing as $expected found")
                }

            }
        }

    }
}