/*
 *  TodayHabitAdapter
 *
 *  Version 1.0
 *
 *  November 4, 2021
 *
 *  Copyright 2021 Rowan Tilroe, Claire Martin, Filippo Ciandy,
 *  Gurbani Baweja, Chanpreet Singh, and Paige Lekach
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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

/**
 * Adapter class for the today view activity.
 *
 * @author Claire Martin
 */
public class TodayHabitAdapter extends ArrayAdapter<Habit> {
    private Context context;
    private User user;

    /**
     * Constructor
     * @param context
     * @param user
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TodayHabitAdapter(Context context, User user) {
        super(context, 0, user.getTodayHabits());
        this.user = user;
        this.context = context;
    }


    /**
     * Gets a view for the list of today's habits so that it can be displayed
     * @param position the position in the list
     * @param convertView
     * @param parent
     * @return the view for the list of today's habits
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.habit_list_content, parent, false);
        }

        Habit habit = user.getTodayHabits().get(position);

        TextView habitTitle = view.findViewById(R.id.habit_title);
        habitTitle.setText(habit.getTitle());

        return view;
    }
}
