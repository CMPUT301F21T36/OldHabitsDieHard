package com.example.oldhabitsdiehard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // made this go straight to the HabitListActivity for now, can change this later
        Intent intent = new Intent(this, HabitListActivity.class);
        startActivity(intent);
    }
}