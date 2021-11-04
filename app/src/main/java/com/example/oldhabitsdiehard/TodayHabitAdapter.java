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

public class TodayHabitAdapter extends ArrayAdapter<Habit> {
    private Context context;
    private User user;

    /**
     * Constructor
     * @param context
     * @param user
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TodayHabitAdapter(Context context, User user) {
        super(context, 0, user.getTodayHabits());
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

        Habit habit = user.getTodayHabits().get(position);

        TextView habitTitle = view.findViewById(R.id.habit_title);
        habitTitle.setText(habit.getTitle());

        return view;
    }
}
