/*
 *  HabitEventAdapter
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
 * Custom adapter for Habit Events
 *
 * @author Rowan Tilroe
 * @author Filippo Ciandy
 * @author Claire Martin
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
            view = LayoutInflater.from(context).inflate(R.layout.habitevent_content, parent,false);
        }
        HabitEvent habitEvent = user.getHabitEvents().get(position);
        TextView habitEventTitle = view.findViewById(R.id.habitevent_title);
        habitEventTitle.setText(habitEvent.getComment());

        return view;
    }
}
