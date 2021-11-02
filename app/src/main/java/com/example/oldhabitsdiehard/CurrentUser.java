package com.example.oldhabitsdiehard;

/**
 * Class that provides global access to the currently logged in user
 * Only one User will be logged into at a time
 *
 * @author Rowan Tilroe
 */
public class CurrentUser {
    private static User currentUser = null;

    /**
     * Private constructor to prevent instantiation
     */
    private CurrentUser() {}

    /**
     * Get reference to currently logged in user (Can be null)
     * @return current user
     */
    public static User get() {
        return currentUser;
    }

    /**
     * Set currently logged in user to another user (can be null)
     * @param user user to be set as logged in
     */
    public static void set(User user) {
        currentUser = user;
    }
}
