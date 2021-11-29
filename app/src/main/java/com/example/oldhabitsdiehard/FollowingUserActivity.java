package com.example.oldhabitsdiehard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FollowingUserActivity extends AppCompatActivity {
    private User user;
    private UserDatabase db;

    /**
     * Declares action to take when this activity is started.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.following_user_view);
        db = UserDatabase.getInstance();
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        user = db.getUser(username);

        Button backButton = findViewById(R.id.back_to_following);

        ArrayList<Habit> userPublicHabits = user.getPublicHabits();
        ListView userHabitList = findViewById(R.id.following_habits_list);
        TextView usernameHeader = findViewById(R.id.following_username);
        usernameHeader.setText(username);
        StaticHabitAdapter staticHabitAdapter = new StaticHabitAdapter(getApplicationContext(), user.getPublicHabits());
        userHabitList.setAdapter(staticHabitAdapter);
        userHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // get clicked habit
                final Habit habit = userPublicHabits.get(position);
                // create fragment to view habit
                FollowingHabitFragment fragment = FollowingHabitFragment.newInstance(habit, username);
                fragment.show(getSupportFragmentManager(), "VIEW_HABIT");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FollowingActivity.class);
                startActivity(intent);
            }
        });
    }
}
