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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class HabitEventFragment extends DialogFragment {

    private EditText habitEventTitle;
    private EditText habitEventComment;
    private EditText habitEventDate;
    private HabitEventFragment.onFragmentInteractionListener listener;

    public interface onFragmentInteractionListener {
        void onOkPressed(Habit newHabit);
    }


    //@RequiresApi(api = Build.VERSION_CODES.O)
    public static HabitEventFragment newInstance(HabitEvent habitEvent) {

        Bundle args = new Bundle();
        args.putSerializable("HABIT EVENT TITLE", habitEvent.getHabit().getTitle());
        args.putSerializable("COMMENTS", habitEvent.getComment());
        args.putSerializable("DATES",String.valueOf(habitEvent.getDate()));

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
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.habit_event_fragment, null);
        habitEventTitle = view.findViewById(R.id.habitEventTitle);
        habitEventComment = view.findViewById(R.id.habitEventComment);
        habitEventDate = view.findViewById(R.id.habitEventDate);

        if (getArguments() != null) {
            habitEventTitle.setText(getArguments().getString("HABIT EVENT TITLE"));
            habitEventComment.setText(getArguments().getString("COMMENTS"));
            habitEventDate.setText(getArguments().getString("DATES"));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setNegativeButton("Cancel",null)
                .setNeutralButton("Delete",new DialogInterface.OnClickListener(){     //For future coding purpose
                    //@RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // listener.onDeletePressed();
                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {     //For future coding purpose
                    //@RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


//                        String title = habitTitle.getText().toString();
//                        String reason = habitReason.getText().toString();
//                        boolean rdb;
//                        if(publicButton.isChecked()){
//                            rdb = true;
//                        }else{
//                            rdb = false;
//                        }
//                        LocalDate date= LocalDate.parse(habitDate.getText().toString());
//
//                        boolean[] days = {sunday.isChecked(),monday.isChecked(),tuesday.isChecked(),wednesday.isChecked(),thursday.isChecked(),friday.isChecked(),saturday.isChecked()};
//
//
//                        listener.onOkPressed(new Habit(title,reason,date, days, rdb));
                    }
                }).create();


    }



}