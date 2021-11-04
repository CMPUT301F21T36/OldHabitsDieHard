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
 * a list of all their habits
 */
public class HabitListActivity extends AppCompatActivity implements AddHabitFragment.onFragmentInteractionListener {
    private ListView habitListView;
    private HabitAdapter habitAdapter;
    private ArrayList<Habit> habitList;

    /**
     * Called upon creation of the activity
     * @param savedInstanceState the saved state
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // view the habit_list xml file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_list);

        //Fetching current user from firebase
        User user = CurrentUser.get();

        // create the habit list and set its adapter
        habitListView = findViewById(R.id.habit_list);
        habitList = user.getHabits();

        habitAdapter = new HabitAdapter(this, habitList);
        habitListView.setAdapter(habitAdapter);

        final FloatingActionButton addCityButton = findViewById(R.id.addHabitButton);
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddHabitFragment().show(getSupportFragmentManager(), "ADD_HABIT");
            }
        });

        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {

                AddHabitFragment fragment = AddHabitFragment.newInstance(habitList.get(position));
                fragment.show(getSupportFragmentManager(), "ADD_HABIT");

                habitList.remove(position);
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

    @Override
    public void onOkPressed(Habit newHabit){habitAdapter.add(newHabit);}

    @Override
    public void onDeletePressed(){habitAdapter.notifyDataSetChanged();}
}