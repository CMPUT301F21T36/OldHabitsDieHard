package com.example.oldhabitsdiehard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Intent;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView habiteventlistview;
    private ArrayAdapter<HabitEvent> habiteventAdapter;
    private ArrayList<HabitEvent> habiteventlist;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitevent_list);
        Intent intent = new Intent(this, HabitEventListActivity.class);
        startActivity(intent);
    }
}