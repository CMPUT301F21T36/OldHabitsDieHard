package com.example.oldhabitsdiehard;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import org.junit.BeforeClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robotium.solo.Solo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for ProfileActivity. UI tests are written here and robotium test framework is used
 * @author Gurbani Baweja, Filippo Ciandy and Paige Lekach
 */
public class ProfileActivityTest {
    private Solo solo;

    /**
     * Runs before class to set the CurrentUser
     */
    @BeforeClass
    public static void settingUser(){
        CurrentUser.set(new User("gurbaniB", "Gurbani18"));;
    }

    @Rule
    public ActivityTestRule<ProfileActivity> rule =
            new ActivityTestRule<>(ProfileActivity.class);

    /**
     * Runs before all tests and creates solo instance
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Navigates to the TodayActivity after the today button on navigation bar is clicked
     */
    @Test
    public void checkTodayClicked(){
        // Asserts that the current activity is ProfileActivity
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        BottomNavigationView nav = rule.getActivity().findViewById(R.id.bottom_navigation);
        // Getting the menu and items so today can be clicked
        Menu menu = nav.getMenu();
        MenuItem item = menu.findItem(R.id.action_today);
        boolean clicked = menu.performIdentifierAction(item.getItemId(), 0);

        // Checking that button was clicked and in correct activity
        assertTrue("Not clicked", clicked);
        solo.assertCurrentActivity("Wrong Activity", TodayActivity.class);
    }

    /**
     * Navigates to the HabitListActivity after the habit button on navigation bar is clicked
     */
    @Test
    public void checkHabitCLicked(){
        // Asserts that the current activity is ProfileActivity
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        BottomNavigationView nav = rule.getActivity().findViewById(R.id.bottom_navigation);
        // Getting the menu and items so habits can be clicked
        Menu menu = nav.getMenu();
        MenuItem item = menu.findItem(R.id.action_habits);
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
        // Asserts that the current activity is ProfileActivity
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
        BottomNavigationView nav = rule.getActivity().findViewById(R.id.bottom_navigation);
        // Getting the menu and items so events can be clicked
        Menu menu = nav.getMenu();
        MenuItem item = menu.findItem(R.id.action_events);
        boolean clicked = menu.performIdentifierAction(item.getItemId(), 0);

        // Checking that button was clicked and in correct activity
        assertTrue("Not clicked", clicked);
        solo.assertCurrentActivity("Wrong Activity", HabitEventListActivity.class);
    }


    /**
     * Navigates to the SearchActivity after the search icon on navigation bar is clicked
     */
    @Test
    public void checkSearchClicked(){
        // Asserts that the current activity is ProfileActivity
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
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
