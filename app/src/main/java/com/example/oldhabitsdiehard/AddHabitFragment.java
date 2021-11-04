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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.time.LocalDate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class AddHabitFragment  extends DialogFragment {

    private EditText habitTitle;
    private EditText habitReason;
    private EditText habitDate;
    RadioButton privateButton;
    RadioButton publicButton;
    CheckBox sunday,monday,tuesday,wednesday, thursday, friday,saturday;
    Boolean rdb;
    private onFragmentInteractionListener listener;

    public interface onFragmentInteractionListener {
        void onOkPressed(Habit newHabit);
        void onDeletePressed();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static AddHabitFragment newInstance(Habit habit) {

        Bundle args = new Bundle();
        args.putSerializable("TITLE", habit.getTitle());
        args.putSerializable("REASON", habit.getReason());
        args.putSerializable("DATE",String.valueOf(habit.getStartDate()));
        args.putSerializable("isPublic",String.valueOf(habit.getPublic()));
        boolean[] w=habit.getWeekdays();
        args.putSerializable("SUN",String.valueOf(w[0]));
        args.putSerializable("MON",String.valueOf(w[1]));
        args.putSerializable("TUE",String.valueOf(w[2]));
        args.putSerializable("WED",String.valueOf(w[3]));
        args.putSerializable("THUR",String.valueOf(w[4]));
        args.putSerializable("FRI",String.valueOf(w[5]));
        args.putSerializable("SAT",String.valueOf(w[6]));

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.habit_fragment, null);
        habitTitle = view.findViewById(R.id.habitTitle);
        habitReason = view.findViewById(R.id.habitReason);
        habitDate = view.findViewById(R.id.startDate);
        privateButton=(RadioButton) view.findViewById(R.id.privateHabit);
        publicButton=(RadioButton) view.findViewById(R.id.publicHabit);
        sunday = view.findViewById(R.id.sundayCheckBox);
        monday = view.findViewById(R.id.mondayCheckBox);
        tuesday = view.findViewById(R.id.tuesdayCheckBox);
        wednesday = view.findViewById(R.id.wednesdayCheckBox);
        thursday = view.findViewById(R.id.thursdayCheckBox);
        friday = view.findViewById(R.id.fridayCheckBox);
        saturday = view.findViewById(R.id.saturdayCheckBox);

        if (getArguments() != null) {
            habitTitle.setText(getArguments().getString("TITLE"));
            habitReason.setText(getArguments().getString("REASON"));
            habitDate.setText(getArguments().getString("DATE"));
            if (Boolean.parseBoolean(getArguments().getString("isPublic"))) {
                publicButton.setChecked(true);
                privateButton.setChecked(false);
            }else{
                privateButton.setChecked(true);
                publicButton.setChecked(false);
            }
            sunday.setChecked(Boolean.parseBoolean(getArguments().getString("SUN")));
            monday.setChecked(Boolean.parseBoolean(getArguments().getString("MON")));
            tuesday.setChecked(Boolean.parseBoolean(getArguments().getString("TUE")));
            wednesday.setChecked(Boolean.parseBoolean(getArguments().getString("WED")));
            thursday.setChecked(Boolean.parseBoolean(getArguments().getString("THUR")));
            friday.setChecked(Boolean.parseBoolean(getArguments().getString("FRI")));
            saturday.setChecked(Boolean.parseBoolean(getArguments().getString("SAT")));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setNegativeButton("Cancel",null)
                .setNeutralButton("Delete",new DialogInterface.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDeletePressed();
                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        String title = habitTitle.getText().toString();
                        String reason = habitReason.getText().toString();
                        boolean rdb;
                        if(publicButton.isChecked()){
                            rdb = true;
                        }else{
                            rdb = false;
                        }
                        LocalDate date= LocalDate.parse(habitDate.getText().toString());

                        boolean[] days = {sunday.isChecked(),monday.isChecked(),tuesday.isChecked(),wednesday.isChecked(),thursday.isChecked(),friday.isChecked(),saturday.isChecked()};


                        listener.onOkPressed(new Habit(title,reason,date, days, rdb));
                    }
                }).create();


    }



}
