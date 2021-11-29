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
 * Test class for CreateAccountActivity. UI tests are written here and robotium test framework is used
 * @author Gurbani Baweja
 */

public class CreateAccountTest {
    private Solo solo;
    private UserDatabase db;

    @Rule
    public ActivityTestRule<CreateAccount> rule =
            new ActivityTestRule<>(CreateAccount.class);

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
     * User creates a profile and then activity switches to  the TodayActivity
     */
    @Test
    public void checkCreateClicked() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", CreateAccount.class);

        // Setting TextEdits to test values
        // Sleeping for sometime
        Thread.sleep(100);

        onView(withId(R.id.username_create)).perform(typeText("Alex1")).perform(closeSoftKeyboard());
        onView(withId(R.id.password_create)).perform(typeText("Alex34")).perform(closeSoftKeyboard());
        onView(withId(R.id.confirm_password)).perform(typeText("Alex34")).perform(closeSoftKeyboard());
        onView(withId(R.id.bio_create)).perform(typeText("bio")).perform(closeSoftKeyboard());

        // Sleeping for sometime
        Thread.sleep(250);

        // Clicking the Sign Up Button
        solo.clickOnButton("Sign Up");

        // Switched activities
        solo.assertCurrentActivity("Wrong Activity", TodayActivity.class);
        assertEquals(CurrentUser.get().getUsername(), "Alex1");

        // Deleting the test user
        db.deleteUser(new User("Alex1", "Alex34"));
    }

    /**
     * Types in username and password and clicks the create button but the username already is created
     * so the Activity is still CreateAccountActivity
     */
    @Test
    public void checkAlreadyCreatedUser() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity", CreateAccount.class);

        // Setting TextEdits to test values
        onView(withId(R.id.username_create)).perform(typeText("GurbaniB")).perform(closeSoftKeyboard());
        onView(withId(R.id.password_create)).perform(typeText("Baweja78")).perform(closeSoftKeyboard());
        onView(withId(R.id.confirm_password)).perform(typeText("Baweja78")).perform(closeSoftKeyboard());
        onView(withId(R.id.bio_create)).perform(typeText("bio")).perform(closeSoftKeyboard());

        // Sleeping for sometime
        Thread.sleep(250);
        //Clicking the Sign Up Button
        solo.clickOnButton("Sign Up");

        // Staying on CreateAccountActivity as user already exists
        solo.assertCurrentActivity("Wrong Activity", CreateAccount.class);
        db.deleteUser(new User("GurbaniB", "Baweja78"));
    }


}
