package com.example.oldhabitsdiehard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Custom adapter for Habit Events
 */
public class HabitEventAdapter extends ArrayAdapter<HabitEvent> {
    private Context context;
    private ArrayList<HabitEvent> habitevents;

    /**
     * Constructor
     * @param context
     * @param habitevents
     */
    public HabitEventAdapter(Context context, HabitEventList habitevents) {
        super(context, 0, habitevents);
        this.context = context;
        this.habitevents = habitevents;
    }

    /**
     * Get view for HabitEventList
     * @param position is position in the list
     * @param convertView
     * @param parent
     * @return view for HabitEventList
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        boolean everyday_boolean = true;
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.habitevent_content, parent,false);
        }
        HabitEvent habitevent = habitevents.get(position);

        TextView habiteventtitle = view.findViewById(R.id.habitevent_title);
        habiteventtitle.setText(habitevent.getComment());

//        // Added extra functionality for event that is done everyday
//        for (int i = 0; i < 7; i++){
//            if (habitevent.getHabit().getWeekdays()[i] == false){
//                everyday_boolean = false;
//            }
//        }
//        if (everyday_boolean){
//            habiteventtitle.setText(habitevent.getHabit().getTitle() + habitevent.getComment() + "(Everyday)");
//        }
//        else {
//            habiteventtitle.setText(habitevent.getHabit().getTitle());
//        }
        return view;
    }
}
