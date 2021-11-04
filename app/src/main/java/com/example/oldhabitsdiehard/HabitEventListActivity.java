package com.example.oldhabitsdiehard;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class shows Habit event list activity where users can see all their habit events.
 */
public class HabitEventListActivity extends AppCompatActivity implements HabitEventFragment.onFragmentInteractionListener{
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
        boolean myweekdays[] = {true, true, true, true, true, true, true};
        Habit habit = new Habit("Exercise","Fat", myweekdays);

        Habit habit2 = new Habit("Exercise2","Fat", myweekdays);

        // Get event list from habit and set adapter
        habiteventlist = habit.getHabitEvents(); // should be empty
        habiteventAdapter = new HabitEventAdapter(this, habiteventlist);
        habiteventlistview.setAdapter(habiteventAdapter);

        // Add test habit event to habiteventlist
        Location location_test = new Location(LocationManager.PASSIVE_PROVIDER);
        LocalDate d= LocalDate.parse("2021-02-03");
        HabitEvent habitevent = new HabitEvent(habit, " at the gym ", d,location_test );
        d= LocalDate.parse("2021-02-05");
        HabitEvent habitevent2 = new HabitEvent(habit2, " sbishb ", d,location_test );
        habiteventlist.add(habitevent);
        habiteventlist.add(habitevent2);


        habiteventlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {

                HabitEventFragment fragment = HabitEventFragment.newInstance(habiteventlist.get(position));
                fragment.show(getSupportFragmentManager(), "HABIT_EVENT");

                //habiteventlist.remove(position);
            }
        });



    }
    @Override
    public void onOkPressed(Habit newHabit){;}

}
