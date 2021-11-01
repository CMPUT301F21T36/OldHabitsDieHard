package com.example.oldhabitsdiehard;

import java.util.ArrayList;

/**
 * This class represents a user
 * @author Rowan Tilroe
 */
public class User {
    private String username;
    private String password;
    private String bio;
    HabitList habits;


    /**
     * User constructor
     * @param username User's username (for login)
     * @param password User's password (for login)
     * @param id
     */
    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
        habits = new HabitList();
    }

    /**
     * Username getter
     * @return User's username
     */
    public String getUsername() { return username; }

    /**
     * Password getter
     * @return User's password
     */
    public String getPassword() { return password; }

    /**
     * Habits getter
     * @return User's HabitList
     */
    public HabitList getHabits() { return habits; }

    /**
     * Bio getter
     * @return User's bio
     */
    public String getBio() { return bio; }

}
