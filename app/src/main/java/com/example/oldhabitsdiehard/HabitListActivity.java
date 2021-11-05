/*
 *  HabitListActivity
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

/**
 * This class represents the Habit List Activity in which the user can view
 * a list of all their habits.
 *
 * @author Claire Martin
 */
public class HabitListActivity extends AppCompatActivity implements HabitFragment.onFragmentInteractionListener {
    private ListView habitListView;
    private HabitAdapter habitAdapter;
    private ArrayList<Habit> habitList;
    private User user;
    private UserDatabase db;

    /**
     * Called upon creation of the activity.
     * @param savedInstanceState the saved state
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // view the habit_list xml file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_list);

        //Fetching current user from firebase
        user = CurrentUser.get();
        db = UserDatabase.getInstance();
        db.updateUser(user);


        // create the habit list and set its adapter
        habitListView = findViewById(R.id.habit_list);
        habitList = user.getHabits();

        // set the adapter
        habitAdapter = new HabitAdapter(this, user);
        habitListView.setAdapter(habitAdapter);

        // define habit add button
        final FloatingActionButton addHabitButton = findViewById(R.id.addHabitButton);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Defines action to take when the add button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                new HabitFragment().show(getSupportFragmentManager(), "ADD_HABIT");
            }
        });

        // functionality for when a habit is clicked
        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Defines action to take when a habit is clicked.
             * @param parent
             * @param view
             * @param position the position of the selected habit in the list
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Habit habit = habitList.get(position);
                HabitFragment newFragment = HabitFragment.newInstance(habit);
                newFragment.show(getSupportFragmentManager(), "EDIT_HABIT");
            }
        });

        //creating intents for the different activities
        Intent intentToday = new Intent(this, TodayActivity.class);
        Intent intentProfile = new Intent(this, ProfileActivity.class);
        Intent intentEvents = new Intent(this, HabitEventListActivity.class);
        Intent intentFollowing = new Intent(this, FollowingActivity.class);

        //initializing navigation in the activity
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //switching through activities
                        switch (item.getItemId()) {
                            case R.id.action_today:
                                startActivity(intentToday);
                                break;
                            case R.id.action_events:
                                startActivity(intentEvents);
                                break;
                            case R.id.action_profile:
                                startActivity(intentProfile);
                                break;
                            case R.id.action_following:
                                startActivity(intentFollowing);
                                break;
                        }
                        return false;
                    }
                });

    }

    /**
     * Method to add a habit to the list using the fragment.
     * @param newHabit the habit to add
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addHabit(Habit newHabit) {
        // add the habit
        habitAdapter.add(newHabit);
        // update the user in firestore
        db.updateUser(user);
    }

    /**
     * Method to edit a habit in the list.
     * @param habit the habit to edit
     */
    @Override
    public void editHabit(Habit habit) {
        // notify the adapter that the list has changed
        habitAdapter.notifyDataSetChanged();
        // udpate the user in firestore
        db.updateUser(user);
    }

    /**
     * Method to delete a habit from the list.
     * @param habit the habit to delete
     */
    @Override
    public void deleteHabit(Habit habit) {
        // delete the habit from the user
        // did not delete from adapter in order to ensure events are deleted too
        user.deleteHabit(habit);
        // notify the adapter
        habitAdapter.notifyDataSetChanged();
        // update user in firestore
        db.updateUser(user);
    }
}