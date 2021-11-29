/*
 *  FollowerActivityTest
 *
 *  Version 1.0
 *
 *  November 28, 2021
 *
 *  Copyright 2021 Rowan Tilroe, Claire Martin, Filippo Ciandy,
 *  Gurbani Baweja, Chanpreet Singh, and Paige Lekach
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

    /**
     * Sets current activity to FollowerActivity
     */
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

