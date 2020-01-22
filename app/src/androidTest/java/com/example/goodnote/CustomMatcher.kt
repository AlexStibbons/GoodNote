package com.example.goodnote

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.example.goodnote.ui.noteList.NoteListRecyclerViewAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

// this whole things is suspicious
class CustomMatcher {

    // SEE TypeSafe VS Bounded here: https://riptutorial.com/android/example/15712/espresso-custom-matchers

    companion object {

        fun withNoteTitle(expected: String): Matcher<View> {
            return object : TypeSafeMatcher<View>() {

                override fun describeTo(description: Description?) {
                    description?.appendText("expted to have title: $expected")
                }

                override fun matchesSafely(item: View?): Boolean {
                    if (item != null && item.findViewById<TextView>(R.id.item_note_title) != null) {
                        val noteTitleView: TextView = item.findViewById(R.id.item_note_title)
                        return noteTitleView.text == expected
                    }
                    return false
                }
            }
        }

        fun withImage(resourceId: Int): Matcher<View> {
            return object : BoundedMatcher<View, ImageView>(ImageView::class.java){
                override fun describeTo(description: Description?) {
                    description!!.appendText("has image drawable resource $resourceId")
                }

                override fun matchesSafely(imageView: ImageView?): Boolean = sameBitmap(imageView!!.context,
                    imageView.drawable, resourceId)
            }
        }

        private fun sameBitmap(context: Context, drawable: Drawable, resourceId: Int): Boolean {
            return true
        }

    }
}