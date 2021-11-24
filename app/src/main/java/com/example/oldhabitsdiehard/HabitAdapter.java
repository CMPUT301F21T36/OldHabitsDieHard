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
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * Custom adapter for Habits
 */
public class HabitAdapter extends ArrayAdapter<Habit> {
    private Context context;
    private User user;

    /**
     * Constructor
     * @param context
     * @param user
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public HabitAdapter(Context context, User user) {
        super(context, 0, user.getHabits());
        this.user = user;
        this.context = context;
    }

    /**
     * Gets a view for this habit list so that it can be displayed
     * @param position the position in the list
     * @param convertView
     * @param parent
     * @return the view for the HabitList
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.habit_list_content, parent, false);
        }

        Habit habit = user.getHabits().get(position);

        TextView habitTitle = view.findViewById(R.id.habit_title);
        habitTitle.setText(habit.getTitle());

        return view;
    }

}
