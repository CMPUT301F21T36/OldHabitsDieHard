package com.example.oldhabitsdiehard;

import static androidx.test.espresso.Espresso.onView;
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
     * Types in username and password and clicks the create button which switches the the TodayActivity
     * and sets the CurrentUser to passed in values
     */
    @Test
    public void checkCreateClicked(){
        // Asserts that the current activity is LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        // Setting TextEdits to test values
        onView(withId(R.id.username_box)).perform(typeText("test_login"));
        onView(withId(R.id.password_box)).perform(typeText("test_login_password"));
        // clicking button
        solo.clickOnButton("Create");
        // Switched activities
        solo.assertCurrentActivity("Wrong Activity", TodayActivity.class);
        assertEquals(CurrentUser.get().getUsername(), "test_login");
        // Deleting the test user
        db.deleteUser(new User("test_login", "test_login_password"));
    }

    /**
     * Types in username and password and clicks the create button but the username already is created
     * so the Activity is still LoginActivity
     */
    @Test
    public void checkAlreadyCreatedUser(){
        // Asserts that the current activity is LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        // Setting TextEdits to test values
        onView(withId(R.id.username_box)).perform(typeText("test_login"));
        onView(withId(R.id.password_box)).perform(typeText("test_login_password"));
        // Adding user to the database
        db.addUser(new User("test_login", "test_login_password"));
        // clicking on create button
        solo.clickOnButton("Create");
        // Stay on LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        db.deleteUser(new User("test_login", "test_login_password"));
    }

    /**
     * User is added to database and then the info is passed in and logged in so the Activity switches
     * to the TodayActivity and the CurrentUser is set to the passed in value
     */
    @Test
    public void checkLoginClicked(){
        // Asserts that the current activity is LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        //Adding user to database
        db.addUser(new User("test_login", "test_login_password"));
        // Setting TextEdits to test values
        onView(withId(R.id.username_box)).perform(typeText("test_login"));
        onView(withId(R.id.password_box)).perform(typeText("test_login_password"));
        // Clicking login button
        solo.clickOnButton("Login");
        // Switching to TodayActivity
        solo.assertCurrentActivity("Wrong Activity", TodayActivity.class);
        assertEquals(CurrentUser.get().getUsername(), "test_login");
        // Deleting test user
        db.deleteUser(new User("test_login", "test_login_password"));
    }

    /**
     * Not created user tries to login so the Activity stays as LoginActivity and doesn't change
     */
    @Test
    public void checkNotCreatedUserLoginClicked(){
        // Asserts that the current activity is LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);
        // Setting TextEdits to test values
        onView(withId(R.id.username_box)).perform(typeText("test_login"));
        onView(withId(R.id.password_box)).perform(typeText("test_login_password"));
        // Clicking login button
        solo.clickOnButton("Login");
        // Should stay in LoginActivity
        solo.assertCurrentActivity("Wrong Activity", Login.class);
    }
}
