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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

/**
 * This class shows Habit event list activity where users can see all their habit events.
 */
public class HabitEventListActivity extends AppCompatActivity implements HabitEventFragment.onFragmentInteractionListener {
    private ListView habitEventListView;
    private HabitEventAdapter habitEventAdapter;
    private ArrayList<HabitEvent> habitEventList;
    private User user;
    private UserDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitevent_list);

        user = CurrentUser.get();
        db = UserDatabase.getInstance();
        db.updateUser(user);

        // Initialization
        habitEventListView = findViewById(R.id.habitevent_list);
        habitEventList = user.getHabitEvents();

        habitEventAdapter = new HabitEventAdapter(this, user);
        habitEventListView.setAdapter(habitEventAdapter);

        final FloatingActionButton addHabitEventButton = findViewById(R.id.add_habit_event_button);
        addHabitEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HabitEventFragment().show(getSupportFragmentManager(), "ADD_HABIT_EVENT");
            }
        });

        habitEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HabitEvent habitEvent = habitEventList.get(position);
                HabitEventFragment newFragment = HabitEventFragment.newInstance(habitEvent);
                newFragment.show(getSupportFragmentManager(), "EDIT_HABIT_EVENT");
            }
        });

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

    @Override
    public void addHabitEvent(HabitEvent newEvent) {
        user.addHabitEvent(newEvent);
        //habitEventAdapter.add(newEvent);
        habitEventAdapter.notifyDataSetChanged();
        db.updateUser(user);
    }

    @Override
    public void editHabitEvent(HabitEvent event) {
        habitEventAdapter.notifyDataSetChanged();
        db.updateUser(user);
    }

    @Override
    public void deleteHabitEvent(HabitEvent event) {
        habitEventAdapter.remove(event);
        db.updateUser(user);
    }
}
