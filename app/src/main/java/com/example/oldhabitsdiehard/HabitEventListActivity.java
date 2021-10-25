package com.example.oldhabitsdiehard;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;

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

        Habit habit = new Habit("Exercise","Fat");
        HabitEvent habitevent = new HabitEvent(habit, 2009-12-01,222.2222);


    }
}
