package com.example.oldhabitsdiehard;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class shows Habit event list activity where users can see all their habit events.
 */
public class HabitEventListActivity extends AppCompatActivity {
    private ListView habiteventlistview;
    private ArrayAdapter<HabitEvent> habiteventAdapter;
    private ArrayList<HabitEvent> habiteventlist;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitevent_list);

        // Initialization of habit event list and adapter of it
        habiteventlistview = findViewById(R.id.habitevent_list);
        habiteventlist = new ArrayList<>();
        habiteventAdapter = new HabitEventList(this, habiteventlist);
        habiteventlistview.setAdapter(habiteventAdapter);
        boolean myweekdays[] = {true, true, true, true, true, true, true};
        Habit habit = new Habit("Exercise","Fat",myweekdays);
        Location location_test = new Location(LocationManager.PASSIVE_PROVIDER);
        HabitEvent habitevent = new HabitEvent(habit, " at the gym ", LocalDate.now(),location_test);
        habiteventlist.add(habitevent);

    }
}
