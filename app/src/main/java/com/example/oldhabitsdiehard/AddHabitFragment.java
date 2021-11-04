package com.example.oldhabitsdiehard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class AddHabitFragment  extends DialogFragment {
    private EditText habitTitle;
    private EditText habitReason;
    private DatePicker habitDate;
    private RadioButton radioSelectedButton;
    private RadioButton privateButton;
    private RadioButton publicButton;
    private CheckBox sunday,monday,tuesday,wednesday, thursday, friday,saturday;
    private Boolean rdb;
    private onFragmentInteractionListener listener;

    public interface onFragmentInteractionListener {
        void addHabit(Habit habit);
        void editHabit(Habit habit);
        void deleteHabit(Habit habit);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static AddHabitFragment newInstance(Habit habit) {

        Bundle args = new Bundle();
        args.putSerializable("Habit", habit);

        AddHabitFragment fragment = new AddHabitFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof onFragmentInteractionListener){
            listener = (onFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString()+"must implement OnFragmentInteractionListner");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.habit_fragment, null);
        habitTitle = view.findViewById(R.id.habitTitle);
        habitReason = view.findViewById(R.id.habitReason);
        habitDate = view.findViewById(R.id.startDate);
        final RadioGroup group = view.findViewById(R.id.radio_group);
        sunday = view.findViewById(R.id.sundayCheckBox);
        monday = view.findViewById(R.id.mondayCheckBox);
        tuesday = view.findViewById(R.id.tuesdayCheckBox);
        wednesday = view.findViewById(R.id.wednesdayCheckBox);
        thursday = view.findViewById(R.id.thursdayCheckBox);
        friday = view.findViewById(R.id.fridayCheckBox);
        saturday = view.findViewById(R.id.saturdayCheckBox);

        // public is default
        group.check(R.id.publicHabit);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (getArguments() != null) {
            // we are editing a habit
            Habit myHabit = (Habit) getArguments().getSerializable("Habit");

            habitTitle.setText(myHabit.getTitle());
            habitReason.setText(myHabit.getReason());
            boolean currPublic = myHabit.getPublic();
            if (currPublic) {
                group.check(R.id.publicHabit);
            } else {
                group.check(R.id.privateHabit);
            }
            sunday.setChecked(myHabit.getWeekdays().get(0));
            monday.setChecked(myHabit.getWeekdays().get(1));
            tuesday.setChecked(myHabit.getWeekdays().get(2));
            wednesday.setChecked(myHabit.getWeekdays().get(3));
            thursday.setChecked(myHabit.getWeekdays().get(4));
            friday.setChecked(myHabit.getWeekdays().get(5));
            saturday.setChecked(myHabit.getWeekdays().get(6));

            //LocalDate myDate = myHabit.getStartDate();
            int year = myHabit.getYear();
            int month = myHabit.getMonth();
            int day = myHabit.getDay();

            habitDate.updateDate(year, month, day);
            return builder
                    .setView(view)
                    .setTitle("Edit Habit")
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.deleteHabit(myHabit);
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int selectedPrivacy = group.getCheckedRadioButtonId();
                            radioSelectedButton = view.findViewById(selectedPrivacy);
                            String privacy = String.valueOf(radioSelectedButton.getText());
                            boolean isPublic;
                            if (privacy.equals("Public")) {
                                isPublic = true;
                            } else {
                                isPublic = false;
                            }

                            String title = habitTitle.getText().toString();
                            String reason = habitReason.getText().toString();
                            int day = habitDate.getDayOfMonth();
                            int month = habitDate.getMonth();
                            int year = habitDate.getYear();
                            LocalDate date = LocalDate.of(year, month, day);

                            List<Boolean> weekdays = new ArrayList<Boolean>();
                            weekdays.add(sunday.isChecked());
                            weekdays.add(monday.isChecked());
                            weekdays.add(tuesday.isChecked());
                            weekdays.add(wednesday.isChecked());
                            weekdays.add(thursday.isChecked());
                            weekdays.add(friday.isChecked());
                            weekdays.add(saturday.isChecked());

                            myHabit.setTitle(title);
                            myHabit.setReason(reason);
                            myHabit.setPublic(isPublic);
                            myHabit.setWeekdays(weekdays);
                            myHabit.setStartDate(date);

                            listener.editHabit(myHabit);
                        }
                    }).create();
        } else {
            // we are adding a habit
            return builder
                    .setView(view)
                    .setTitle("Add Habit")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String title = habitTitle.getText().toString();
                            String reason = habitReason.getText().toString();
                            int selectedPrivacy = group.getCheckedRadioButtonId();
                            radioSelectedButton = view.findViewById(selectedPrivacy);
                            String privacy = String.valueOf(radioSelectedButton.getText());
                            boolean isPublic;
                            if (privacy.equals("Public")) {
                                isPublic = true;
                            } else {
                                isPublic = false;
                            }
                            int day = habitDate.getDayOfMonth();
                            int month = habitDate.getMonth();
                            int year = habitDate.getYear();
                            LocalDate date = LocalDate.of(year, month, day);

                            List<Boolean> weekdays = new ArrayList<Boolean>();
                            weekdays.add(sunday.isChecked());
                            weekdays.add(monday.isChecked());
                            weekdays.add(tuesday.isChecked());
                            weekdays.add(wednesday.isChecked());
                            weekdays.add(thursday.isChecked());
                            weekdays.add(friday.isChecked());
                            weekdays.add(saturday.isChecked());

                            listener.addHabit(new Habit(title, reason, date, weekdays, isPublic));
                        }
                    }).create();
        }

    }



}
