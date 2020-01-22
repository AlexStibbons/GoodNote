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

// this whole things is incorrect
class CustomMatcher {

    // SEE TypeSafe VS Bounded here: https://riptutorial.com/android/example/15712/espresso-custom-matchers

    companion object {

        // this one is suspicious
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