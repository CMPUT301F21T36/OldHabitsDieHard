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
 * Provides a custom list for Habit objects
 */
public class HabitList extends ArrayList<Habit> {

    /**
     * Constructor
     */
    public HabitList() {
        super();
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
        for (int i = 0; i < this.size(); i++) {
            // iterate through habits in list
            if (this.get(i).getWeekdays()[today]) {
                // habit i is performed on this day
                todayList.add(this.get(i));
            }
        }
        return todayList;
    }
}
