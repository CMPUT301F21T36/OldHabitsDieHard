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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.util.ArrayList;

public class HabitEventFragment extends DialogFragment {
    private User user;
    private Spinner habitEventType;
    private EditText habitEventComment;
    private DatePicker habitEventDate;
    private HabitEventFragment.onFragmentInteractionListener listener;

    public interface onFragmentInteractionListener {
        void addHabitEvent(HabitEvent event);
        void editHabitEvent(HabitEvent event); // implemented later
        void deleteHabitEvent(HabitEvent event); // implemented later
    }


    //@RequiresApi(api = Build.VERSION_CODES.O)
    public static HabitEventFragment newInstance(HabitEvent habitEvent) {
        Bundle args = new Bundle();
        args.putSerializable("HabitEvent", habitEvent);

        HabitEventFragment fragment = new HabitEventFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof HabitEventFragment.onFragmentInteractionListener){
            listener = (HabitEventFragment.onFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString()+"must implement OnFragmentInteractionListner");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        user = CurrentUser.get();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.habit_event_fragment, null);
        habitEventType = view.findViewById(R.id.habitEventType);
        habitEventComment = view.findViewById(R.id.habitEventComment);
        habitEventDate = view.findViewById(R.id.habitEventDate);

        //get the spinner from the xml.
        Spinner dropdown = view.findViewById(R.id.habitEventType);
        //create a list of items for the spinner.
        ArrayList<Habit> habits = user.getHabits();
        ArrayList<String> habitNames = new ArrayList<String>();
        for (int i = 0; i < habits.size(); i++) {
            habitNames.add(habits.get(i).getTitle());
        }
        //create an adapter to describe how the habitNames are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, habitNames);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (getArguments() != null) {
            // we are viewing a habitEvent
            HabitEvent myEvent = (HabitEvent) getArguments().getSerializable("HabitEvent");

            //habitEventTitle.setText(myEvent.getHabit().getTitle());
            habitEventComment.setText(myEvent.getComment());

            // set which habit type is selected
            String title = myEvent.getHabit();
            int spinnerPos = adapter.getPosition(title);
            habitEventType.setSelection(spinnerPos);

            int year = myEvent.getYear();
            int month = myEvent.getMonth();
            int day = myEvent.getDay();

            habitEventDate.updateDate(year, month, day);
            return builder
                    .setView(view)
                    .setNegativeButton("Cancel", null)
                    .create();
                    //.setNeutralButton("Delete", new DialogInterface.OnClickListener() {     //For future coding purpose
                        //@RequiresApi(api = Build.VERSION_CODES.O)
                        //@Override
                        //public void onClick(DialogInterface dialogInterface, int i) {
                            // to be implemented later
                        //}
                    //})
                    //.setPositiveButton("Edit", new DialogInterface.OnClickListener() {     //For future coding purpose
                        //@RequiresApi(api = Build.VERSION_CODES.O)
                        //@Override
                        //public void onClick(DialogInterface dialogInterface, int i) {
                            // to be implemented later
                        //}

        } else {
            // we are adding a habit event
            return builder
                    .setView(view)
                    .setTitle("Add Habit")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //String title = habitEventTitle.getText().toString();
                            int pos = habitEventType.getSelectedItemPosition();
                            Habit habit = habits.get(pos);
                            String habitName = habit.getTitle();

                            String comment = habitEventComment.getText().toString();
                            int day = habitEventDate.getDayOfMonth();
                            int month = habitEventDate.getMonth();
                            int year = habitEventDate.getYear();
                            LocalDate date = LocalDate.of(year, month, day);

                            listener.addHabitEvent(new HabitEvent(habitName, comment, date));
                        }
                    }).create();
        }
    }
}