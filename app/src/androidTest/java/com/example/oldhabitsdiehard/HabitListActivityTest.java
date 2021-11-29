package com.example.oldhabitsdiehard;

import static org.junit.Assert.assertTrue;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for HabitListActivity. UI tests are written here and robotium test framework is used
 * @author Gurbani Baweja, Filippo Ciandy and Paige Lekach
 */

public class HabitListActivityTest {
    private Solo solo;

    /**
     * Runs before class to set the CurrentUser
     */
    @BeforeClass
    public static void settingUser() {
        CurrentUser.set(new User("gurbaniB", "Gurbani18"));

    }

    @Rule
    public ActivityTestRule<HabitListActivity> rule = new ActivityTestRule<>(HabitListActivity.class);


    /**
     * Runs before all tests and creates solo instance
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Navigates to the TodayActivity after the today button on navigation bar is clicked
     */
    @Test
    public void checkTodayClicked(){
        // Asserts that the current activity is HabitListActivity
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        BottomNavigationView nav = rule.getActivity().findViewById(R.id.bottom_navigation);
        // Getting the menu and items so today can be clicked
        Menu menu = nav.getMenu();
        MenuItem item = menu.findItem(R.id.action_today);
        boolean clicked = menu.performIdentifierAction(item.getItemId(), 0);

        // Checking that button was clicked and in correct activity
        assertTrue("Not clicked", clicked);
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
    }


    /**
     * Navigates to the HabitEventListActivity after the events button on navigation bar is clicked
     */
    @Test
    public void checkHabitEventClicked(){
        // Asserts that the current activity is HabitListActivity
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        BottomNavigationView nav = rule.getActivity().findViewById(R.id.bottom_navigation);
        // Getting the menu and items so events can be clicked
        Menu menu = nav.getMenu();
        MenuItem item = menu.findItem(R.id.action_events);
        boolean clicked = menu.performIdentifierAction(item.getItemId(), 0);

        // Checking that button was clicked and in correct activity
        assertTrue("Not clicked", clicked);
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
    }


    /**
     * Navigates to the ProfileActivity after the profile button on navigation bar is clicked
     */
    @Test
    public void checkProfileClicked(){
        // Asserts that the current activity is HabitListActivity
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        BottomNavigationView nav = rule.getActivity().findViewById(R.id.bottom_navigation);
        // Getting the menu and items so profile can be clicked
        Menu menu = nav.getMenu();
        MenuItem item = menu.findItem(R.id.action_profile);
        boolean clicked = menu.performIdentifierAction(item.getItemId(), 0);

        // Checking that button was clicked and in correct activity
        assertTrue("Not clicked", clicked);
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
    }


    /**
     * Navigates to the SearchActivity after the search icon on navigation bar is clicked
     */
    @Test
    public void checkSearchClicked(){
        // Asserts that the current activity is ProfileActivity
        solo.assertCurrentActivity("Wrong Activity", HabitListActivity.class);
        BottomNavigationView nav = rule.getActivity().findViewById(R.id.bottom_navigation);
        // Getting the menu and items so following can be clicked
        Menu menu = nav.getMenu();
        MenuItem item = menu.findItem(R.id.action_search);
        boolean clicked = menu.performIdentifierAction(item.getItemId(), 0);

        // Checking that button was clicked and in correct activity
        assertTrue("Not clicked", clicked);
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
    }


}
