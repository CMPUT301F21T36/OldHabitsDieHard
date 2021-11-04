package com.example.oldhabitsdiehard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
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

        // create the habit list and set its adapter
        habitListView = findViewById(R.id.habit_list);
        habitList = new ArrayList<Habit>();

        habitAdapter = new HabitAdapter(this, habitList);
        habitListView.setAdapter(habitAdapter);

        // added some test habits to list to ensure it displays properly
        Habit habit = new Habit("Eat Breakfast", "Hungry");
        Habit habit2 = new Habit("Yoga", "Exercise");
        habitList.add(habit);
        habitList.add(habit2);
        Habit habit3 = new Habit("Eat Breakfast", "Hungry");
        boolean[] b = {true,true,false,false,false,false,false};
        LocalDate d= LocalDate.parse("2021-02-03");
        Habit habit4 = new Habit("YogaFull", "Exercise",d,b,false);
        habitList.add(habit3);
        habitList.add(habit4);


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



    }

    @Override
    public void onOkPressed(Habit newHabit){habitAdapter.add(newHabit);}

    @Override
    public void onDeletePressed(){habitAdapter.notifyDataSetChanged();}
}