package com.example.oldhabitsdiehard;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for LoginActivity. UI tests are written here and robotium test framework is used
 */
public class LoginActivityTest {
    private Solo solo;
    private UserDatabase db;

    @Rule
    public ActivityTestRule<Login> rule =
            new ActivityTestRule<>(Login.class);

    /**
     * Runs before all tests and creates solo instance
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        db = UserDatabase.getInstance();
    }

    /**
     * Simple user login case switching to TodayActivity
     */
    @Test
    public void checkLogin() throws InterruptedException {
        // Asserts that the current activity is Login
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        db.addUser(new User("GurB","B@aw11111"));
        // Setting TextEdits to test values
        onView(withId(R.id.username_login)).perform(typeText("GurB")).perform(closeSoftKeyboard());
        //solo.assertCurrentActivity("Wrong Activity", Login.class);
        Thread.sleep(250);
        onView(withId(R.id.password_login)).perform(typeText("B@aw11111")).perform(closeSoftKeyboard());
        //onView(withId(R.id.bio_create)).perform(typeText("bio!"));
        // clicking button
        Thread.sleep(250);

        solo.clickOnButton("Sign In");
        // Switched activities
        solo.assertCurrentActivity("Wrong Activity", TodayActivity.class);
        assertEquals(CurrentUser.get().getUsername(), "GurB");
        // Deleting the test user
        db.deleteUser(new User("GurB", "B@aw11111"));
    }

    /**
     * Not created user state, stays at Login Page
     */
    @Test
    public void checkNonExisitingUser() throws InterruptedException {
        // Asserts that the current activity is LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        // Navigating to create profile
        //solo.clickOnButton("Create Profile");
        // Setting TextEdits to test values
        onView(withId(R.id.username_login)).perform(typeText("TestMe")).perform(closeSoftKeyboard());
        Thread.sleep(250);
        onView(withId(R.id.password_login)).perform(typeText("Test01")).perform(closeSoftKeyboard());
        // clicking on sign in button
        Thread.sleep(250);
        solo.clickOnButton("Sign In");
        // Stay on LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);

    }


}
