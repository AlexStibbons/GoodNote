package com.example.goodnote;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.test.espresso.matcher.BoundedMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;


public class CustomMatcherJava {

    public static Matcher<View> withBackgroundAndForeground(final int backgroundId, final int foregroundId) {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("temp");
            }
            @Override
            protected boolean matchesSafely(View item) {
                Drawable backgroundOnView = item.getBackground();
                Drawable foregroundOnView = item.getForeground();
                Boolean matchesExpectedBackground = sameBitmap(item.getContext(), backgroundOnView, backgroundId);
                Boolean matchesExpectedForeground = sameBitmap(item.getContext(), foregroundOnView, foregroundId);
                return matchesExpectedBackground && matchesExpectedForeground;
            }
        };
    }
    public static Matcher<View> withBackground(final int backgroundId) {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("temp");
            }
            @Override
            protected boolean matchesSafely(View item) {
                Drawable backgroundOnView = item.getBackground();
                Boolean matchesExpectedBackground = sameBitmap(item.getContext(), backgroundOnView, backgroundId);
                return matchesExpectedBackground;
            }
        };
    }
    private static Boolean sameBitmap(Context context, Drawable drawable, int resourceId) {
        Drawable otherDrawable = context.getResources().getDrawable(resourceId);
        if (drawable == null || otherDrawable == null) {
            return false;
        }
        return areDrawablesIdentical(drawable, otherDrawable);
    }

    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateB != null && stateA.equals(stateB))
                || getBitmap(drawableA).sameAs(getBitmap(drawableB));
    }

    public static Bitmap getBitmap(Drawable drawable) {
        Bitmap result;
        if (drawable instanceof BitmapDrawable) {
            result = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // Some drawables have no intrinsic width - e.g. solid colours.
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return result;
    }
}
