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
    private User user;

    /**
     * Constructor
     * @param context
     * @param user
     */
    public HabitEventAdapter(Context context, User user) {
        super(context, 0, user.getHabitEvents());
        this.user = user;
        this.context = context;
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
        HabitEvent habitEvent = user.getHabitEvents().get(position);

        TextView habitEventTitle = view.findViewById(R.id.habitevent_title);
        habitEventTitle.setText(habitEvent.getComment());

        return view;
    }
}
