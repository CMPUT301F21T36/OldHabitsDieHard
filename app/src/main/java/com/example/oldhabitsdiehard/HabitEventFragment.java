/*
 *  HabitEventFragment
 *
 *  Version 1.0
 *
 *  November 4, 2021
 *
 *  Copyright 2021 Rowan Tilroe, Claire Martin, Filippo Ciandy,
 *  Gurbani Baweja, Chanpreet Singh, and Paige Lekach
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.oldhabitsdiehard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A class for the fragment allowing the user to add, edit, view or delete
 * habit events.
 * @author Gurbani Baweja
 * @author Claire Martin
 */
public class HabitEventFragment extends DialogFragment {
    private User user;
    private Spinner habitEventType;
    private EditText habitEventComment;
    private DatePicker habitEventDate;
    private HabitEventFragment.onFragmentInteractionListener listener;

    /**
     * A listener interface for this fragment to interact with the calling activity.
     */
    public interface onFragmentInteractionListener {
        // abstract methods to be implemented in the activity classes
        void addHabitEvent(HabitEvent event);
        void editHabitEvent(HabitEvent event); // implemented later
        void deleteHabitEvent(HabitEvent event); // implemented later
    }

    /**
     * Creates an instance of the HabitEventFragment.
     * @param habitEvent the habit event we are looking at
     * @return the fragment
     */
    public static HabitEventFragment newInstance(HabitEvent habitEvent) {
        Bundle args = new Bundle();
        args.putSerializable("HabitEvent", habitEvent);

        HabitEventFragment fragment = new HabitEventFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Defines action to take when fragment is attached.
     * @param context the context of the current activity
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof HabitEventFragment.onFragmentInteractionListener){
            listener = (HabitEventFragment.onFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString()+"must implement OnFragmentInteractionListner");
        }
    }

    /**
     * Defines action to take when the fragment is created
     * @param savedInstanceState
     * @return the dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // get the current user
        user = CurrentUser.get();

        // set the view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.habit_event_fragment, null);

        // get view for habit event info objects
        habitEventType = view.findViewById(R.id.habitEventType);
        habitEventComment = view.findViewById(R.id.habitEventComment);
        habitEventDate = view.findViewById(R.id.habitEventDate);
        Spinner dropdown = view.findViewById(R.id.habitEventType);

        // create a list of habits to populate the spinner
        // the user can only select habits which are already part of the current user
        ArrayList<Habit> habits = user.getHabits();
        ArrayList<String> habitNames = new ArrayList<String>();
        for (int i = 0; i < habits.size(); i++) {
            habitNames.add(habits.get(i).getTitle());
        }
        // create an adapter to describe how the habitNames are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, habitNames);
        dropdown.setAdapter(adapter);

        // build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments() != null) {
            // we are editing a habitEvent
            HabitEvent myEvent = (HabitEvent) getArguments().getSerializable("HabitEvent");
            habitEventComment.setText(myEvent.getComment());

            // set which habit type is selected
            String title = myEvent.getHabit();
            int spinnerPos = adapter.getPosition(title);
            habitEventType.setSelection(spinnerPos);

            // get the date info
            int year = myEvent.getYear();
            int month = myEvent.getMonth();
            int day = myEvent.getDay();
            habitEventDate.updateDate(year, month, day);
            return builder
                    .setView(view)
                    .setTitle("Edit Habit")
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        /**
                         * Defines action to take when delete button is pressed
                         * @param dialogInterface the dialog interface
                         * @param i
                         */
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.deleteHabitEvent(myEvent);
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // get the habit name
                            int pos = habitEventType.getSelectedItemPosition();
                            Habit habit = habits.get(pos);
                            String habitName = habit.getTitle();

                            // get the comment for the habit event
                            String comment = habitEventComment.getText().toString();

                            // get the date info for the habit event
                            int day = habitEventDate.getDayOfMonth();
                            int month = habitEventDate.getMonth();
                            int year = habitEventDate.getYear();
                            LocalDate date = LocalDate.of(year, month, day);

                            // update event with new info
                            myEvent.setHabit(habitName);
                            myEvent.setComment(comment);
                            myEvent.setDate(date);

                            // add the habit event to the listener
                            listener.editHabitEvent(myEvent);
                        }
                    }).create();
        } else {
            // we are adding a habit event
            return builder
                    .setView(view)
                    .setTitle("Add Habit")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        /**
                         * Defines action to take when the OK button is pressed.
                         * @param dialogInterface the dialog interface
                         * @param i
                         */
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // get the selected habit name
                            int pos = habitEventType.getSelectedItemPosition();
                            Habit habit = habits.get(pos);
                            String habitName = habit.getTitle();

                            // get the comment for the habit event
                            String comment = habitEventComment.getText().toString();

                            // get the date info for the habit event
                            int day = habitEventDate.getDayOfMonth();
                            int month = habitEventDate.getMonth();
                            int year = habitEventDate.getYear();
                            LocalDate date = LocalDate.of(year, month, day);

                            // add the habit event to the listener
                            listener.addHabitEvent(new HabitEvent(habitName, comment, date));
                        }
                    }).create();
        }
    }
}