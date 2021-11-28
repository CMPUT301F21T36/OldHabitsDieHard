package com.example.oldhabitsdiehard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class FollowingHabitFragment extends DialogFragment {
    private TextView habitTitle;
    private TextView habitReason;
    private TextView habitDate;
    private TextView habitWeekdays;
    private TextView habitHeader;

    public static FollowingHabitFragment newInstance(Habit habit, String user) {
        Bundle args = new Bundle();
        args.putSerializable("Habit", habit);
        args.putSerializable("User", user);
        FollowingHabitFragment fragment = new FollowingHabitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // get habit fragment view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.following_habit_fragment, null);

        // get textview objects
        habitTitle = view.findViewById(R.id.following_habit_title);
        habitReason = view.findViewById(R.id.following_habit_reason);
        habitDate = view.findViewById(R.id.following_habit_date);
        habitWeekdays = view.findViewById(R.id.following_habit_weekdays);
        habitHeader = view.findViewById(R.id.following_habit_header);
        // build alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (getArguments() != null) {
            // we have clicked a habit
            Habit habit = (Habit) getArguments().getSerializable("Habit");
            String user = (String) getArguments().getSerializable("User");
            // set textviews
            habitTitle.setText("Title: " + habit.getTitle());
            habitReason.setText("Reason: " + habit.getReason());

            // convert date to string
            String day = Integer.toString(habit.getDay());
            int monthVal = habit.getMonth();
            String month = "";
            switch (monthVal) {
                case 1:
                    month = "Jan";
                    break;
                case 2:
                    month = "Feb";
                    break;
                case 3:
                    month = "Mar";
                    break;
                case 4:
                    month = "Apr";
                    break;
                case 5:
                    month = "May";
                    break;
                case 6:
                    month = "Jun";
                    break;
                case 7:
                    month = "Jul";
                    break;
                case 8:
                    month = "Aug";
                    break;
                case 9:
                    month = "Sept";
                    break;
                case 10:
                    month = "Oct";
                    break;
                case 11:
                    month = "Nov";
                    break;
                case 12:
                    month = "Dec";
                    break;
            }
            String year = Integer.toString(habit.getYear());

            habitDate.setText("Start Date: " + month + ". " + day + ", " + year);

            List<Boolean> weekdaysList = habit.getWeekdays();
            String weekdays = "Days of Week: ";
            if (weekdaysList.get(0)) {
                weekdays += "Sun, ";
            }
            if (weekdaysList.get(1)) {
                weekdays += "Mon, ";
            }
            if (weekdaysList.get(2)) {
                weekdays += "Tues, ";
            }
            if (weekdaysList.get(3)) {
                weekdays += "Wed, ";
            }
            if (weekdaysList.get(4)) {
                weekdays += "Thurs, ";
            }
            if (weekdaysList.get(5)) {
                weekdays += "Fri, ";
            }
            if (weekdaysList.get(6)) {
                weekdays += "Sat";
            }

            if (weekdays.substring(weekdays.length()-2).equals(", ")) {
                weekdays = weekdays.substring(0, weekdays.length()-2);
            }
            habitWeekdays.setText(weekdays);
            habitHeader.setText(user + "'s Habit");
            return builder
                    .setView(view)
                    .setNeutralButton("Cancel", null)
                    .create();
        }
        return null;
    }
}
