package com.example.oldhabitsdiehard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import android.content.Context;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class HabitListTest {
    @Test
    void testTodayList() {
        //Context context;
        boolean[] weekdays = {true, true, true, true, true, true, true};
        Habit habit = new Habit("", "", weekdays);
        ArrayList<Habit> habitList = new ArrayList<Habit>();
        habitList.add(habit);
        //HabitList habitAdapter = new HabitList(null, habitList);
        //assertEquals(habitList, habitAdapter.todayHabits());
    }
}
