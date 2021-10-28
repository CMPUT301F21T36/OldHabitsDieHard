package com.example.oldhabitsdiehard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This class represents the Habit List Activity in which the user can view
 * a list of all their habits
 */
public class HabitListActivity extends AppCompatActivity {
    private ListView habitListView;
    private ArrayAdapter<Habit> habitAdapter;
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

        // create the habit list and set its adapter
        habitListView = findViewById(R.id.habit_list);
        habitList = new ArrayList<>();
        habitAdapter = new HabitList(this, habitList);
        habitListView.setAdapter(habitAdapter);

        // added some test habits to list to ensure it displays properly
        Habit habit = new Habit("Eat Breakfast", "Hungry");
        Habit habit2 = new Habit("Yoga", "Exercise");
        habitList.add(habit);
        habitList.add(habit2);
    }
}