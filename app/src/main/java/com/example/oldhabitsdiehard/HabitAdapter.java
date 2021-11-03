package com.example.oldhabitsdiehard;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Custom adapter for Habits
 */
public class HabitAdapter extends ArrayAdapter<Habit> {
    private Context context;
    private ArrayList<Habit> habits;

    /**
     * Constructor
     * @param context
     * @param habits
     */
    public HabitAdapter(Context context, ArrayList<Habit> habits) {
        super(context, 0, habits);
        this.habits = habits;
        this.context = context;
    }

    /**
     * Gets a view for this habit list so that it can be displayed
     * @param position the position in the list
     * @param convertView
     * @param parent
     * @return the view for the HabitList
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.habit_list_content, parent, false);
        }

        Habit habit = habits.get(position);

        TextView habitTitle = view.findViewById(R.id.habit_title);
        habitTitle.setText(habit.getTitle());

        return view;
    }
}
