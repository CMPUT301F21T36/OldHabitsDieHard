package com.example.oldhabitsdiehard;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class shows Habit event list activity where users can see all their habit events.
 */
public class HabitEventListActivity extends AppCompatActivity {
    private ListView habiteventlistview;
    private HabitEventAdapter habiteventAdapter;
    private ArrayList<HabitEvent> habiteventlist;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitevent_list);

        // Initialization
        habiteventlistview = findViewById(R.id.habitevent_list);
        habiteventlistview.setAdapter(habiteventAdapter);

        // Create test habit
        List<Boolean> weekdays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7])); // initialized to false
        Collections.fill(weekdays, Boolean.FALSE);
        Habit habit = new Habit("Exercise","Fat", weekdays);

        // Get event list from habit and set adapter
        habiteventlist = habit.getHabitEvents(); // should be empty
        habiteventAdapter = new HabitEventAdapter(this, habiteventlist);
        habiteventlistview.setAdapter(habiteventAdapter);

        // Add test habit event to habiteventlist
        Location location_test = new Location(LocationManager.PASSIVE_PROVIDER);
        HabitEvent habitevent = new HabitEvent(habit, " at the gym ", LocalDate.now(), location_test);
        habiteventlist.add(habitevent);

        //creating intents for the different activities
        Intent intentToday = new Intent(this, TodayActivity.class);
        Intent intentProfile = new Intent(this, ProfileActivity.class);
        Intent intentHabits = new Intent(this, HabitListActivity.class);
        Intent intentFollowing = new Intent(this, FollowingActivity.class);

        //setting up the navigation bar in the activity
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //switching between the different actions
                        switch (item.getItemId()) {
                            case R.id.action_today:
                                startActivity(intentToday);
                                break;
                            case R.id.action_habits:
                                startActivity(intentHabits);
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
}
