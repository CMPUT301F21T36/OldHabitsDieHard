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
 * Custom list adapter for Habit event
 */
public class HabitEventList extends ArrayAdapter<HabitEvent> {

    private final Context context;
    private ArrayList<HabitEvent> habitevents;

    /**
     * Constructor for HabitEventList
     * @param context is context
     * @param habitevents is an ArrayList containing HabitEvent objects
     */
    public HabitEventList(@NonNull Context context, ArrayList<HabitEvent> habitevents) {
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
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.habitevent_content, parent,false);
        }
        HabitEvent habitevent = habitevents.get(position);
        TextView habiteventtitle = view.findViewById(R.id.habitevent_title);
        habiteventtitle.setText(habitevent.getHabit().getTitle());

        return view;
    }
}
