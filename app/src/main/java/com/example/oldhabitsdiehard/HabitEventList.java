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
 * Custom list for Habit events
 */
public class HabitEventList extends ArrayList<HabitEvent> {

    /**
     * Constructor
     */
    public HabitEventList() {
        super();
    }

    // TODO: Method to sort by date?
}
