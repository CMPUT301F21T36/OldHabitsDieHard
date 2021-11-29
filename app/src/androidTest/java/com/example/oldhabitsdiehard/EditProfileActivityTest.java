package com.example.oldhabitsdiehard;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.view.Menu;
import android.view.MenuItem;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;


/**
 * Test class for EditProfileActivity. UI tests are written here and robotium test framework is used
 * @author Gurbani Baweja
 */
public class EditProfileActivityTest {
    private Solo solo;

    /**
     * Runs before class to set the CurrentUser
     */
    @BeforeClass
    public static void settingUser() {
        CurrentUser.set(new User("gurbaniB", "Gurbani18"));

    }

    @Rule
    public ActivityTestRule<EditProfileActivity> rule =
            new ActivityTestRule<>(EditProfileActivity.class);

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
     * Navigates to the Edit Profile Section
     */
    @Test
    public void editProfileCheck() {
        // Asserts that the current activity is EditProfileActivity
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);
        // Edits the user bio
        onView(withId(R.id.bio_edit)).perform(typeText("hello")).perform(closeSoftKeyboard());
        // Save Changes button clicked
        solo.clickOnButton("Save Changes");
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);
    }

    /**
     * Navigates to the Edit Password Section
     */
    @Test
    public void editPasswordCheck() {
        // Asserts that the current activity is EditProfileActivity
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);
        // Setting the text fields required to edit password
        onView(withId(R.id.current_password)).perform(typeText("Gurbani18")).perform(closeSoftKeyboard());
        onView(withId(R.id.password_new_edit)).perform(typeText("Gurbani19")).perform(closeSoftKeyboard());
        onView(withId(R.id.password_confirm_edit)).perform(typeText("Gurbani19")).perform(closeSoftKeyboard());
        // Save Password button clicked
        solo.clickOnButton("Save Password");
        solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);
    }


}


