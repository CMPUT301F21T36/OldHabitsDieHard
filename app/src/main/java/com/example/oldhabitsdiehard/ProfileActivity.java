/*
 *  ProfileActivity
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
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * **NOT IMPLEMENTED**
 * This class creates an activity for the user to view their profile.
 *
 * @author Paige Lekach
 */
public class ProfileActivity extends AppCompatActivity {
    private User user;
    UserDatabase db = UserDatabase.getInstance();

    /**
     * Defines action to take when activity is created.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        user = db.getUser(CurrentUser.get().getUsername());
        TextView username = findViewById(R.id.profile_username);
        TextView bio = findViewById(R.id.bio_profile);
        TextView followingTitle = findViewById(R.id.following_title);
        TextView noFollowingTitle = findViewById(R.id.no_following_header);
        TextView followersCount = findViewById(R.id.profile_followers_count);
        TextView followingCount = findViewById(R.id.profile_following_count);
        LinearLayout followerLayout = findViewById(R.id.followers_layout);
        LinearLayout followingLayout = findViewById(R.id.following_layout);

        Button editProfileButton = findViewById(R.id.edit_profile);

        followersCount.setText(String.valueOf(user.getFollowers().size()));
        followingCount.setText(String.valueOf(user.getFollowing().size()));

        if(user.getFollowRequests().size() > 0 ){
            followingTitle.setVisibility(View.VISIBLE);
            noFollowingTitle.setVisibility(View.INVISIBLE);
        }
        ListView followRequestsView = findViewById(R.id.follow_request_list);
        FollowRequestAdapter followRequestAdapter = new FollowRequestAdapter(this, user);
        followRequestsView.setAdapter(followRequestAdapter);

        username.setText(CurrentUser.get().getUsername());
        bio.setText(CurrentUser.get().getBio());

        // creating intents for different activities
        Intent intentToday = new Intent(this, TodayActivity.class);
        Intent intentHabits = new Intent(this, HabitListActivity.class);
        Intent intentEvents = new Intent(this, HabitEventListActivity.class);
        Intent intentSearch = new Intent(this, SearchActivity.class);


        editProfileButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Defines action to take when the create button is clicked
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        followerLayout.setOnClickListener(new View.OnClickListener() {
            /**
             * Defines action to take when the create button is clicked
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FollowerActivity.class);
                startActivity(intent);
            }
        });

        followingLayout.setOnClickListener(new View.OnClickListener() {
            /**
             * Defines action to take when the create button is clicked
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FollowingActivity.class);
                startActivity(intent);
            }
        });
        followRequestAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        //initializing navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    /**
                     * Defines action to take when the navigation buttons are pressed.
                     * @param item the button that was pressed
                     * @return false if the activity fails to start
                     */
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //switching through activities
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
                            case R.id.action_search:
                                startActivity(intentSearch);
                                break;
                        }
                        return false;
                    }
                });
    }
}
