package com.example.oldhabitsdiehard;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Database for user data
 * Connects to Firestore
 *
 * (Collection) Users
 *      (Document) username1 -> (User) obj
 *      (Document) username2 -> (User) obj
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
        if (check != null) {
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
        User result = null;

        DocumentReference userDocRef = userCollection.document(username);
        Task<DocumentSnapshot> task = userDocRef.get();
        while (!task.isComplete()) {}
        result = task.getResult().toObject(User.class);

        return result;
    }

    /**
     * Updates the user's state in the database
     * @param user User to update
     * @return True if update successful, false if unsuccessful (i.e. User does not exist in
     */
    public boolean updateUser(User user) {
        User check = getUser(user.getUsername());
        if (user == null) {
            return false;
        }
        else {
            userCollection.document(user.getUsername()).set(user);
            return true;
        }
    }

    /**
     * Attempt to delete user from database
     * @param user User to delete
     * @return true if deletion successful, false if unsuccessful (i.e. User doesn't exist)
     */
    public boolean deleteUser(User user) {
        User check = getUser(user.getUsername());
        if (user == null) {
            return false;
        }
        else {
            userCollection.document(user.getUsername()).delete();
            return true;
        }
    }

    /**
     * Attempt to verify login information
     * @param username
     * @param password
     * @return NULL if login information fails, User object if login information correct
     */
    public User checkLogin(String username, String password) {
        User user = getUser(username);
        if (user == null) {
            return null;
        }
        else {
            if (password.equals(user.getPassword())) {
                return user;
            }
            else {
                return null;
            }
        }
    }
}
