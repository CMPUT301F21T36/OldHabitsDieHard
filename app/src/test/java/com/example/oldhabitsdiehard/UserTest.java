package com.example.oldhabitsdiehard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Tests for the User class
 */
public class UserTest {
    private User mockUser() {
        String username = "mockUser";
        String password = "mockPass";
        User user = new User(username, password);
        return user;
    }

    @Test
    void testConstructor() {
        User user = mockUser();
        assertEquals("mockUser", user.getUsername());
        assertEquals("mockPass", user.getPassword());
    }

    @Test
    void testGetUser() {
        User user = mockUser();
        assertEquals("mockUser", user.getUsername());
    }

    @Test
    void testGetPassword() {
        User user = mockUser();
        assertEquals("mockPass", user.getPassword());
    }

    @Test
    void testGetBio() {
        User user = mockUser();
        user.setBio("bio");
        assertEquals("bio", user.getBio());
    }

    @Test
    void testGetHabits() {
        User user = mockUser();
        Habit habit = new Habit("title", "reason");
        user.addHabit(habit);
        assertTrue(user.getHabits().contains(habit));
    }

    @Test
    void testSetUsername() {
        User user = mockUser();
        user.setUsername("user");
        assertEquals("user", user.getUsername());
    }

    @Test
    void testSetPassword() {
        User user = mockUser();
        user.setPassword("pass");
        assertEquals("pass", user.getPassword());
    }

    @Test
    void testSetBio() {
        User user = mockUser();
        user.setBio("bio");
        assertEquals("bio", user.getBio());
        user.setBio("newBio");
        assertEquals("newBio", user.getBio());
    }

    @Test
    void testTodayHabits() {
        boolean[] weekdays = {true, true, true, true, true, true, true};
        Habit habit = new Habit("", "", weekdays);
        User user = mockUser();
        user.addHabit(habit);
        ArrayList<Habit> todayHabits = user.getTodayHabits();
        assertTrue(todayHabits.contains(habit));
    }

}
