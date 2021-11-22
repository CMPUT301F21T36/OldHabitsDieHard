package com.example.oldhabitsdiehard;

/**
 * Class that holds follow requests between users.
 * Follow requests are held by the followee (user that is to be followed).
 *
 * @author Rowan Tilroe
 */
public class FollowRequest {
    private String follower; // User that wants to follow
    private String followee; // User that is to be followed

    // Empty constructor for Firestore compatibility
    public FollowRequest() {}

    /**
     * Constructor
     * @param follower
     * @param followee
     */
    public FollowRequest(String follower, String followee) {
        this.follower = follower;
        this.followee = followee;
    }

    /**
     * Followee getter
     * @return followee (User that is to be followed)
     */
    public String getFollowee() { return followee; }

    /**
     * Follower getter
     * @return follower
     */
    public String getFollower() { return follower; }

    /**
     * Remove the follow request
     */
    private void delete() {
        UserDatabase db = UserDatabase.getInstance();
        User followeeUser = db.getUser(followee);
        followeeUser.getFollowRequests().remove(this);
        db.updateUser(followeeUser);
    }

    /**
     * Accept the follow request
     */
    public void accept() {
        // Get the users
        UserDatabase db = UserDatabase.getInstance();
        User followerUser = db.getUser(follower);
        User followeeUser = db.getUser(followee);

        // Add the follower to the followee's followers list
        followeeUser.getFollowers().add(follower);

        // Add the followee to the follower's following list
        followerUser.getFollowing().add(followee);

        // Update database with changes
        db.updateUser(followerUser);
        db.updateUser(followeeUser);

        // Remove follow request
        delete();
    }

    /**
     * Deny the follow request
     */
    public void deny() {
        // Do nothing and delete the request
        delete();
    }

}
