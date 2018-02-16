package com.example.ajaychaurasia.mycountry;

import com.example.ajaychaurasia.mycountry.ui.ListViewFragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNull;

/**
 * Local unit tests, which will execute on the development machine (host).
 *
 */
@RunWith(RobolectricTestRunner.class)
public class UnitTests {

    //Testing initiation of ListViewFragment
    @Test
    public void testInitiateFragment() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.initiateFragment();
        assertTrue((activity.getSupportFragmentManager().findFragmentByTag(activity.getResources().getString(R.string.list_fragment_tag)))instanceof ListViewFragment);
    }

    // Testing ActionBar title when Custom String is sent to the update method
    @Test
    public void testActionBarForCustomString() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.updateActionBarTitle("TestString");
        assertTrue(activity.getSupportActionBar().getTitle().equals("TestString"));
    }

    // Testing ActionBar title when null String is sent to the update method
    // App Name should be set as title in such case
    @Test
    public void testActionBarForDefaultString() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.updateActionBarTitle(null);
        assertTrue(activity.getSupportActionBar().getTitle().equals(activity.getResources().getString(R.string.app_name)));
    }

    @Test
    public void testNullifyingOfJSONData() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.initiateFragment();
        ListViewFragment listViewFragment = (ListViewFragment) activity.getSupportFragmentManager().findFragmentByTag(activity.getResources().getString(R.string.list_fragment_tag));
        listViewFragment.updateViewWithErrorScreen();
        assertNull(listViewFragment.getJsonResponseData());
    }
}