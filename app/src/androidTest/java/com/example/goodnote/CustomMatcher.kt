package com.example.goodnote

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import androidx.core.graphics.drawable.toBitmap


// this whole things is suspicious
class CustomMatcher {

    // SEE TypeSafe VS Bounded here: https://riptutorial.com/android/example/15712/espresso-custom-matchers

    companion object {

        fun withNoteTitle(expected: String): Matcher<View> {
            return object : TypeSafeMatcher<View>() {

                override fun describeTo(description: Description?) {
                    description?.appendText("expected to have title: $expected")
                }

                override fun matchesSafely(item: View?): Boolean {
                    // the view here is the entire note item, material card + constraint + elements
                    // if the view itself is not null and the note_title can be found
                    // then find the text view
                    if (item?.findViewById<TextView>(R.id.item_note_title) != null) {
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

        fun withBackgroundAndForeground(backgroundResource: Int, foregroundResource: Int): Matcher<View> {
            return object : BoundedMatcher<View, View>(View::class.java){
                override fun describeTo(description: Description?) {
                    description!!.appendText("has image drawable resource")
                }

                override fun matchesSafely(view: View?): Boolean {
                    val backgroundOnView = view?.background // drawable
                    val foregroundOnView = view?.foreground // drawable
                    val boolBkg = sameBitmap(view!!.context, backgroundOnView!!, backgroundResource)
                    val boolForeg = sameBitmap(view.context, foregroundOnView!!, foregroundResource)

                    return boolBkg && boolForeg
                }
            }
        }

        private fun sameBitmap(context: Context, drawable: Drawable, resourceId: Int): Boolean {

            var otherDrawable = context.resources.getDrawable(resourceId)
            if (drawable == null || otherDrawable == null) {
                return false
            }

    /*        when {
                (drawable is Drawable) &&
                        (otherDrawable is Drawable) -> true
                }*/


            return drawable.toBitmap().sameAs(otherDrawable.toBitmap())
        }
    }
}