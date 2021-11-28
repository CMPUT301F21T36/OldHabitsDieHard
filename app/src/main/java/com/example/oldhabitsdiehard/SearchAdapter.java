package com.example.oldhabitsdiehard;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SearchAdapter extends ArrayAdapter<Habit> {
    private Context context;
    private User user;
    private UserDatabase db = UserDatabase.getInstance();

    /**
     * Constructor
     * @param context
     * @param user
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public SearchAdapter(Context context, User user) {
        super(context, 0, user.getPublicHabits());
        this.user = user;
        this.context = context;
    }

    /**
     * Get view for a list of HabitEvents.
     * @param position position in the list
     * @param convertView
     * @param parent
     * @return the view
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.habit_list_content, parent,false);
        }
        Habit habit = user.getPublicHabits().get(position);
        TextView habitTitle = view.findViewById(R.id.habit_title);
        habitTitle.setText(habit.getTitle());
        return view;
    }
}
