package com.example.oldhabitsdiehard;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Collections;

/**
 * This class represents the Habit List Activity in which the user can view
 * a list of all their habits
 */
public class HabitListActivity extends AppCompatActivity implements AddHabitFragment.onFragmentInteractionListener {
    private ListView habitListView;
    private HabitAdapter habitAdapter;
    private ArrayList<Habit> habitList;
    private User user;
    private UserDatabase db;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;

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
        user = CurrentUser.get();
        db = UserDatabase.getInstance();
        db.updateUser(user);


        // create the habit list and set its adapter
        //habitListView = findViewById(R.id.habit_list);
        habitList = user.getHabits();

        //habitAdapter = new HabitAdapter(this, user);
        //habitListView.setAdapter(habitAdapter);
        recyclerView = findViewById(R.id.habit_list);
        recyclerAdapter = new RecyclerAdapter(habitList);
        recyclerView.setAdapter(recyclerAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


            final FloatingActionButton addHabitButton = findViewById(R.id.addHabitButton);
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddHabitFragment().show(getSupportFragmentManager(), "ADD_HABIT");
            }
        });

        habitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Habit habit = habitList.get(position);
                AddHabitFragment newFragment = AddHabitFragment.newInstance(habit);
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
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END,0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(habitList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void addHabit(Habit newHabit) {
        habitAdapter.add(newHabit);
        //db = UserDatabase.getInstance();
        db.updateUser(user);
    }

    @Override
    public void editHabit(Habit habit) {
        habitAdapter.notifyDataSetChanged();
        //db = UserDatabase.getInstance();
        db.updateUser(user);
    }

    @Override
    public void deleteHabit(Habit habit) {
        //habitAdapter.remove(habit);
        user.deleteHabit(habit);
        habitAdapter.notifyDataSetChanged();
        //db = UserDatabase.getInstance();
        db.updateUser(user);
    }
}