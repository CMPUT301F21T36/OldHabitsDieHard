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
 * @author Gurbani Baweja
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
     * Simple existing user login case switching to TodayActivity
     */
    @Test
    public void checkLogin() throws InterruptedException {
        // Asserts that the current activity is Login
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        db.addUser(new User("GurB","B@aw11111"));

        // Setting TextEdits to test values
        onView(withId(R.id.username_login)).perform(typeText("GurB")).perform(closeSoftKeyboard());
        Thread.sleep(250);  ////Sleeping for sometime
        onView(withId(R.id.password_login)).perform(typeText("B@aw11111")).perform(closeSoftKeyboard());
        Thread.sleep(250);

        //Clicking the Sign In Button
        solo.clickOnButton("Sign In");

        // Switching to TodayActivity
        solo.assertCurrentActivity("Wrong Activity", TodayActivity.class);
        assertEquals(CurrentUser.get().getUsername(), "GurB");
        // Deleting the test user
        db.deleteUser(new User("GurB", "B@aw11111"));
    }

    /**
     * Not created user state, stays at Login Page
     */
    @Test
    public void checkNonExisistingUser() throws InterruptedException {
        // Asserts that the current activity is LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        // Setting TextEdits to test values
        onView(withId(R.id.username_login)).perform(typeText("TestMe")).perform(closeSoftKeyboard());
        Thread.sleep(250);   //Sleeping for sometime
        onView(withId(R.id.password_login)).perform(typeText("Test01")).perform(closeSoftKeyboard());
        Thread.sleep(250);   //Sleeping for sometime
        // Clicking on the Sign In button
        solo.clickOnButton("Sign In");

        // Staying on LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);

    }


}
