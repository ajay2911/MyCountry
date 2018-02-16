package com.example.ajaychaurasia.mycountry;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented tests, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class InstrumentedTests {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.ajaychaurasia.mycountry", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    // This tests the ListViewFragment was successfully attached to the UI
    @Test
    public void fragmentLayoutVisibilityTest(){
        onView(withId(R.id.list_view_frag)).check(matches(isDisplayed()));
    }

    // This tests the RecyclerView was successfully displayed on UI
    @Test
    public void visibilityOfRecyclerView(){
        // Adding wait time for RecyclerView to render on UI
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_list)).check(matches(isDisplayed()));
    }

    // This is a hardcoded test for ActionBar title
    @Test
    public void testActionBar(){
        // Adding wait time for API to come back with data
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("About Canada")).check(matches(isDisplayed()));
    }

    // This tests positive scenario of app received response and data was displayed in RecyclerView after a pull to refresh
    @Test
    public void swipeGestureTest() {
        onView(withId(R.id.list_view_frag)).perform(swipeDown());
        // Adding wait time for API to come back with data
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_list)).check(matches(isDisplayed()));
    }

    // This tests behaviour of app when Pull refresh is done when not connected to internet
    @Test
    public void swipeGestureTestWhenNoInternet() {
        onView(withId(R.id.list_view_frag)).perform(swipeDown());
        // Adding sufficient wait time for Timeout to happen
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.error_message)).check(matches(isDisplayed()));
    }

    // This tests behaviour of app when not connected to internet
    @Test
    public void checkErrorScreen(){
        onView(withId(R.id.error_message)).check(matches(isDisplayed()));
    }
}
