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

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Provides a custom list adapter for Habit objects
 */
public class HabitList extends ArrayAdapter<Habit> {
    private ArrayList<Habit> habits;
    private Context context;

    /**
     * Constructs a HabitList object
     * @param context the current context
     * @param habits an ArrayList of Habit objects
     */
    public HabitList(Context context, ArrayList<Habit> habits) {
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

    /**
     * Returns a list of Habits from the original HabitList that are scheduled
     * for today
     * @return the list of habits for today
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Habit> todayHabits() {
        ArrayList<Habit> todayList = new ArrayList<Habit>();
        // 1 is Monday, 7 is Sunday
        int today = LocalDate.now().getDayOfWeek().getValue();
        if (today == 7) {
            // it's sunday, switch to 0 to match our notation
            today = 0;
        }
        for (int i = 0; i < habits.size(); i++) {
            // iterate through habits in list
            if (habits.get(i).getWeekdays()[today]) {
                // habit i is performed on this day
                todayList.add(habits.get(i));
            }
        }
        return todayList;
    }
}
