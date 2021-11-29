/*
 *  UserTest
 *
 *  Version 2.0
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;

import java.util.List;

/**
 * Tests for the User class.
 *
 * @author Rowan Tilroe
 * @author Claire Martin
 */
public class UserTest {

    /**
     * Creates a fake user for use in the following tests.
     * @return a user
     */
    private User mockUser() {
        String username = "mockUser";
        String password = "mockPass";
        User user = new User(username, password);
        return user;
    }

    /**
     * Tests the User constructor. Creates a user and then checks to make sure
     * its username and password are correct.
     */
    @Test
    void testConstructor() {
        User user = mockUser();
        assertEquals("mockUser", user.getUsername());
        assertEquals("mockPass", user.getPassword());
    }

    /**
     * Tests the getUsername() method. Checks to make sure the mock user's
     * username is returned correctly.
     */
    @Test
    void testGetUsername() {
        User user = mockUser();
        assertEquals("mockUser", user.getUsername());
    }

    /**
     * Tests the getPassword() method. Checks to make sure the mock user's
     * password is returned correctly.
     */
    @Test
    void testGetPassword() {
        User user = mockUser();
        assertEquals("mockPass", user.getPassword());
    }

    /**
     * Tests the getBio() method. Sets the bio and then checks to make sure it
     * is returned correctly.
     */
    @Test
    void testGetBio() {
        User user = mockUser();
        user.setBio("bio");
        assertEquals("bio", user.getBio());
    }

    /**
     * Tests the addHabit() and deleteHabit() methods. Adds a habit and checks
     * if it is in the habit list, and then deletes it and checks if it was
     * removed.
     */
    @Test
    void testHabitsHandling() {
        User user = mockUser();
        Habit habit = new Habit("title", "reason");

        // Adding and getting
        user.addHabit(habit);
        assertTrue(user.getHabits().contains(habit));

        // Deleting and getting
        user.deleteHabit(habit);
        assertFalse(user.getHabits().contains(habit));
    }

    /**
     * Tests the setUsername() method. Sets the username of our mock user
     * and then checks to make sure it is returned correctly.
     */
    @Test
    void testSetUsername() {
        User user = mockUser();
        user.setUsername("user");
        assertEquals("user", user.getUsername());
    }

    /**
     * Tests the setPassword() method. Sets the password and checks to make sure
     * it was updated.
     */
    @Test
    void testSetPassword() {
        User user = mockUser();
        user.setPassword("pass");
        assertEquals("pass", user.getPassword());
    }

    /**
     * Tests the setBio() method. Sets the new bio and checks to make sure it
     * was updated properly.
     */
    @Test
    void testSetBio() {
        User user = mockUser();
        user.setBio("bio");
        assertEquals("bio", user.getBio());
        user.setBio("newBio");
        assertEquals("newBio", user.getBio());
    }

    /**
     * Tests the todayHabits() method. Creates a mock habit that is supposed to
     * be done every day, adds it to the user and checks to make sure it is
     * in the todayHabits list.
     */
    @Test
    void testTodayHabits() {
        // initialize false boolean array
        List<Boolean> myWeekdays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7]));
        // set all weekdays to true
        Collections.fill(myWeekdays, Boolean.TRUE);
        Habit habit = new Habit("", "", myWeekdays);

        // create mock user and add habit
        User user = mockUser();
        user.addHabit(habit);

        // check to make sure habit is in the today habits list
        ArrayList<Habit> todayHabits = user.getTodayHabits();
        assertTrue(todayHabits.contains(habit));
    }

    /**
     * Test to make sure the class throws an illegal argument exception
     * when a user is created with an empty username and password.
     */
    @Test
    void testIllegalArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("", "");
        });
    }

    /**
     * Tests the addHabitEvent() method. Creates a user, adds a habit, and then
     * adds a habit event for that habit, then checks to make sure the event is
     * in the list.
     */
    @Test
    void testHabitEventHandling() {
        User user = mockUser();
        Habit habit = new Habit("title", "reason");
        user.addHabit(habit);

        // Adding
        HabitEvent event = new HabitEvent("title","comment", LocalDate.of(2021, 11, 4));
        user.addHabitEvent(event);

        // Getting
        assertTrue(user.getHabitEvents().contains(event));
    }

    /**
     * Tests the getPublicHabits() method. Creates two habits, one private and
     * one public, and then checks to make sure only the public habit is returned
     * in the public habits list.
     */
    @Test
    void testGetPublicHabits() {
        User user = mockUser();

        Habit publicHabit = new Habit();
        publicHabit.setPublic(true);

        Habit privateHabit = new Habit();
        privateHabit.setPublic(false);

        user.addHabit(privateHabit);
        user.addHabit(publicHabit);

        ArrayList<Habit> publicHabits = user.getPublicHabits();
        assertTrue(publicHabits.size() == 1);
    }
}
