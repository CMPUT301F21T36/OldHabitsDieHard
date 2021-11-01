package com.example.oldhabitsdiehard;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Database for user data
 * Connects to Firestore
 *
 * (Collection) Users
 *      (Document) username1 -> "name" : (String) username1
 *                              "object" : (User) obj
 *      (Document) username2 -> "name": (String) username2
 *                              "object" : (User) obj
 *
 * @author Rowan Tilroe
 */
public class UserDatabase {
    private static final UserDatabase instance = new UserDatabase();
    private FirebaseFirestore database;
    private CollectionReference userCollection;


    /**
     * Private constructor
     */
    private UserDatabase() {
        database = FirebaseFirestore.getInstance();
        userCollection = database.collection("Users");
    }

    /**
     * Get an instance of the UserDatabase
     * @return handle to UserDatabase
     */
    public static UserDatabase getInstance() { return instance; }

    /**
     * Attempt to add user to database
     * @param user User to add
     * @return True if added, false if not added (i.e. user with username already exists)
     */
    public boolean addUser(User user) {
        User check = getUser(user.getUsername());
        if (check == null) {
            return false;
        }
        else {
            userCollection.document(user.getUsername()).set(user);
            return true;
        }
    }

    /**
     * Attempt to get User with given username
     * @param username Username of user to find
     * @return User found (NULL if no user found)
     */
    public User getUser(String username) {
        Query filter = userCollection.whereEqualTo("name", username);
        Task<QuerySnapshot> results = filter.get();

    }

    /**
     * Attempt to verify login information
     * @param username
     * @param password
     * @return NULL if login information fails, User object if login information correct
     */
    public User checkLogin(String username, String password) {

    }
}
