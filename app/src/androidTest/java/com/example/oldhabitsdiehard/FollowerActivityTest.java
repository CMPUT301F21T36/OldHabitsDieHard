package com.example.oldhabitsdiehard;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for FollowerActivity. UI tests are written here and robotium test framework is used
 * @author Gurbani Baweja
 */
public class FollowerActivityTest {
    private Solo solo;

    /**
     * Runs before class to set the CurrentUser
     */
    @BeforeClass
    public static void settingUser(){
        CurrentUser.set(new User("gurbaniB", "Gurbani18"));;
    }

    @Rule
    public ActivityTestRule<FollowerActivity> rule =
            new ActivityTestRule<>(FollowerActivity.class);

    /**
     * Runs before all tests and creates solo instance
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Navigating to the following option to see the following users
     */
    @Test
    public void checkFollowingClicked(){
        // Asserts that the current activity is FollowerActivity
        solo.assertCurrentActivity("Wrong Activity", FollowerActivity.class);
        // Click on the Following list option
        onView(withId(R.id.following_header)).perform(click());
        // Navigating back to the Profile page
        onView(withId(R.id.back_profile_following)).perform(click());
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }
}

