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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * **NOT IMPLEMENTED YET**
 * Class that displays the Following page, which shows a list of users that the
 * current user is following. This is just a placeholder, the following
 * functionality will be implemented in Project Part 4.
 *
 * @author Paige Lekach
 */
public class FollowerActivity extends AppCompatActivity {
    private User user;

    /**
     * Declares action to take when this activity is started.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers_view);
        UserDatabase db = UserDatabase.getInstance();
        user = db.getUser(CurrentUser.get().getUsername());

        Button backArrow = findViewById(R.id.back_profile_following);
        TextView followingHeader = findViewById(R.id.following_header);
        @SuppressLint("WrongViewCast") TextView followerLayout = findViewById(R.id.followers_layout);

        ListView followingList = findViewById(R.id.following_list_1);
        ListView followersList = findViewById(R.id.follower_list_1);

        FollowingAdapter followingAdapter = new FollowingAdapter(this, user);
        FollowerAdapter followerAdapter = new FollowerAdapter(this, user);

        followingList.setAdapter(followingAdapter);
        followersList.setAdapter(followerAdapter);

        followingHeader.setOnClickListener(new View.OnClickListener() {
            /**
             * Define action to take when login button is clicked.
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FollowingActivity.class);
                startActivity(intent);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            /**
             * Define action to take when login button is clicked.
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}