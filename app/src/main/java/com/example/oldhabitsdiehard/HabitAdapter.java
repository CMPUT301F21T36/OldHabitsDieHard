package com.example.oldhabitsdiehard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Custom adapter for Habits.
 *
 * @author Filippo Ciandy
 */

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder>{

    private ArrayList<Habit> HabitList;
    private AdapterView.OnItemClickListener ItemClickListener;


    /**
     * Constructor
     * @param HabitList
     * @param ItemClickListener
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public HabitAdapter(ArrayList<Habit> HabitList,AdapterView.OnItemClickListener ItemClickListener) {
        this.HabitList = HabitList;
        this.ItemClickListener = ItemClickListener;
    }

    /**
     * Inflates row layout from habit_list_content
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.habit_list_content,parent,false);

        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;

    }

    /**
     * Binds Habit title in dedicated position to TextView in each row.
     * @param holder
     * @param _
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int _) {
        int position = holder.getAdapterPosition();
        Habit habit = HabitList.get(position);

        // Habit title
        holder.textView.setText(habit.getTitle());

        // Habit score indicator
        int score = habit.followScore();
        if (score == 3) {
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_score3habit, 0);
        }
        else if (score == 2) {
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_score2habit, 0);
        }
        else if (score == 1) {
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_score1habit, 0);
        }
        else {
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_score0habit, 0);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemClickListener.onItemClick(null,null,position,position);
            }
        });
    }

    /**
     * Get total number of rows
     * @return total number of rows
     */

    @Override
    public int getItemCount() {
        return HabitList.size();
    }

    /**
     * Recycles and stores view when list is scrolled out of the screen
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.habit_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
