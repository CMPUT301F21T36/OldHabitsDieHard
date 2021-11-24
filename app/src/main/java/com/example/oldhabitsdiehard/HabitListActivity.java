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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collections;

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
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;

    /**
     * Called upon creation of the activity
     *
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
        habitList = user.getHabits();
        recyclerView = findViewById(R.id.habit_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new RecyclerAdapter(habitList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Habit habit = habitList.get(position);
                HabitFragment newFragment = HabitFragment.newInstance(habit);
                newFragment.show(getSupportFragmentManager(), "EDIT_HABIT");
            }
        });
        // set the adapter
        recyclerView.setAdapter(recyclerAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // define habit add button
        final FloatingActionButton addHabitButton = findViewById(R.id.add_habit_button);
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
     * ItemTouchHelper handles callbacks when a drag or swipe action is done.
     */
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(habitList, fromPosition, toPosition);
            db.updateUser(user);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    /**
     * Method to add a habit to the list using the fragment.
     * @param newHabit the habit to add
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addHabit(Habit newHabit) {
        habitList.add(newHabit);
        recyclerAdapter.notifyItemInserted(habitList.size()-1);
        //db = UserDatabase.getInstance();
        db.updateUser(user);
    }

    /**
     * Method to edit a habit in the list.
     * @param habit the habit to edit
     */
    @Override
    public void editHabit(Habit habit) {
        recyclerAdapter.notifyDataSetChanged();
        //db = UserDatabase.getInstance();
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
        recyclerAdapter.notifyDataSetChanged();
        //db = UserDatabase.getInstance();
        db.updateUser(user);
    }
}