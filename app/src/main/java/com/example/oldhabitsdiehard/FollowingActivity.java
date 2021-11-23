/*
 *  FollowingActivity
 *
 *  Version 1.0
 *
 *  November 4, 2021
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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * **NOT IMPLEMENTED YET**
 * Class that displays the Following page, which shows a list of users that the
 * current user is following. This is just a placeholder, the following
 * functionality will be implemented in Project Part 4.
 *
 * @author Paige Lekach
 */
public class FollowingActivity extends AppCompatActivity {

    /**
     * Declares action to take when this activity is started.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.following_view);

        //---------------------------- EXAMPLE --------------------------------
        // get the current logged in user
        User user = CurrentUser.get();
        UserDatabase db = UserDatabase.getInstance();
        db.updateUser(user);

        // make "follower" user try send follow request to this user
        User follower = db.getUser("follower");
        follower.requestToFollow(user);

        // Set up list
        ListView followRequestList = findViewById(R.id.followrequest_list);
        FollowRequestAdapter adapter = new FollowRequestAdapter(this, user);
        followRequestList.setAdapter(adapter);
        //---------------------------- END EXAMPLE ----------------------------


        //creating intents for different activities
        Intent intentHabits = new Intent(this, HabitListActivity.class);
        Intent intentEvents = new Intent(this, HabitEventListActivity.class);
        Intent intentToday = new Intent(this, TodayActivity.class);
        Intent intentProfile = new Intent(this, ProfileActivity.class);

        //initializing navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    /**
                     * Declares action to take when a navigation item is selected.
                     * @param item the item that was selected
                     * @return false if the activity is not started correctly
                     */
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //switching through actions
                        switch (item.getItemId()) {
                            case R.id.action_today:
                                startActivity(intentToday);
                                break;
                            case R.id.action_habits:
                                startActivity(intentHabits);
                                break;
                            case R.id.action_events:
                                startActivity(intentEvents);
                                break;
                            case R.id.action_profile:
                                startActivity(intentProfile);
                                break;

                        }
                        return false;
                    }
                });
    }
}
