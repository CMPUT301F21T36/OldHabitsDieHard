package com.example.oldhabitsdiehard;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private String TAG = "RecyclerAdapter";
    private ArrayList<Habit> HabitList;
    private Context context;
    private User user;
    private AdapterView.OnItemClickListener ItemClickListener;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public RecyclerAdapter(ArrayList<Habit> HabitList,AdapterView.OnItemClickListener ItemClickListener) {
        //super(context, 0, user.getHabits());
        this.HabitList = HabitList;
        //this.user = user;
        //this.context = context;
        //this.HabitList = user.getHabits();
        this.ItemClickListener = ItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.habit_list_content,parent,false);

        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int _) {
        int position = holder.getAdapterPosition();
        holder.textView.setText(HabitList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemClickListener.onItemClick(null,null,position,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return HabitList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView textView, rowCountTextView;


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
