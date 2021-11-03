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
    ArrayList<Habit> habits;


    /**
     * User constructor
     * @param username User's username (for login)
     * @param password User's password (for login)
     */
    public User(String username, String password) throws IllegalArgumentException {
        setUsername(username);
        setPassword(password);
        setBio("");
        habits = new ArrayList<Habit>();
    }
    public User() {}

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
    public ArrayList<Habit> getHabits() { return habits; }

    /**
     * Bio getter
     * @return User's bio
     */
    public String getBio() { return bio; }

    /**
     * Username setter
     * @param username
     * @throws IllegalArgumentException
     */
    public void setUsername(String username) throws IllegalArgumentException {
        if (username.length() < 1) {
            throw new IllegalArgumentException();
        }
        else {
            this.username = username;
        }
    }

    /**
     * Password setter
     * @param password
     * @throws IllegalArgumentException
     */
    public void setPassword(String password) throws IllegalArgumentException {
        if (password.length() < 1) {
            throw new IllegalArgumentException();
        }
        else {
            this.password = password;
        }
    }

    /**
     * Bio setter
     * @param bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }
}
