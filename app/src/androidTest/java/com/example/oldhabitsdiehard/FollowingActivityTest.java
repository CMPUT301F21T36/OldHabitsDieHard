package com.example.oldhabitsdiehard;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.ContentView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import org.junit.BeforeClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robotium.solo.Solo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for FollowingActivity. UI tests are written here and robotium test framework is used
 * @author Gurbani Baweja
 */
public class FollowingActivityTest {
    private Solo solo;

    /**
     * Runs before class to set the CurrentUser
     */
    @BeforeClass
    public static void settingUser(){
        CurrentUser.set(new User("gurbaniB", "Gurbani18"));;
    }

    @Rule
    public ActivityTestRule<FollowingActivity> rule =
            new ActivityTestRule<>(FollowingActivity.class);

    /**
     * Runs before all tests and creates solo instance
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Navigating to the followers option to see the followers
     */
    @Test
    public void checkFollowerClicked(){
        // Asserts that the current activity is FollowingActivity
        solo.assertCurrentActivity("Wrong Activity", FollowingActivity.class);
        // Click on the Followers list option
        onView(withId(R.id.follower_header)).perform(click());
        // Navigating back to the Profile page
        onView(withId(R.id.back_profile_following)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }
}
