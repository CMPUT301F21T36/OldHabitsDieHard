package com.example.oldhabitsdiehard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SearchActivity extends AppCompatActivity {
    private User user;
    private User searchUser;
    private UserDatabase db = UserDatabase.getInstance();;
    /**
     * Declares action to take when this activity is started.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        user = db.getUser(CurrentUser.get().getUsername());

        SearchView searchView = findViewById(R.id.search_bar);
        TextView searchResultHeader = findViewById(R.id.search_result_header);
        TextView searchUsername = findViewById(R.id.search_username);
        Button requestButton = findViewById(R.id.request_search);
        TextView habitsHeader = findViewById(R.id.habits_header_search);
        ListView userHabitList = findViewById(R.id.user_habits);

        //creating intents for different activities
        Intent intentHabits = new Intent(this, HabitListActivity.class);
        Intent intentEvents = new Intent(this, HabitEventListActivity.class);
        Intent intentToday = new Intent(this, TodayActivity.class);
        Intent intentProfile = new Intent(this, ProfileActivity.class);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                UserDatabase db = UserDatabase.getInstance();
                searchUser = db.getUser(s);
                SearchAdapter searchAdapter = new SearchAdapter(getApplicationContext(), searchUser);
                userHabitList.setAdapter(searchAdapter);
                if(searchUser != null){
                    if(!searchUser.getUsername().equals(user.getUsername())){
                        searchResultHeader.setVisibility(View.VISIBLE);
                        searchUsername.setVisibility(View.VISIBLE);
                        searchUsername.setText(s);
                        requestButton.setVisibility(View.VISIBLE);
                        if(user.getFollowing().contains(searchUser.getUsername())){
                            habitsHeader.setVisibility(View.VISIBLE);
                            userHabitList.setVisibility(View.VISIBLE);
                            requestButton.setText("Following");
                            requestButton.setTextColor(getResources().getColor(R.color.blue));
                            requestButton.setBackgroundColor(getResources().getColor(R.color.pink));
                        } else if(searchUser.getFollowRequests().contains(new FollowRequest(user.getUsername(), searchUser.getUsername()))){
                            requestButton.setText("Requested");
                            requestButton.setTextColor(getResources().getColor(R.color.blueLight)); //lb
                            requestButton.setBackgroundColor(getResources().getColor(R.color.blue) ); //b
                            habitsHeader.setVisibility(View.INVISIBLE);
                            userHabitList.setVisibility(View.INVISIBLE);
                        } else{
                            requestButton.setText("Request");
                            requestButton.setTextColor(getResources().getColor(R.color.blue));//b
                            requestButton.setBackgroundColor(getResources().getColor(R.color.blueLight)); //lb
                            habitsHeader.setVisibility(View.INVISIBLE);
                            userHabitList.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No user matches search\nTry again!", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Define action to take when login button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                searchUser = db.getUser(searchUsername.getText().toString());
                if(requestButton.getText().toString().equals("Request")) {
                    // add to following list and switch the visible button
                    searchUser.addFollowRequest(new FollowRequest(CurrentUser.get().getUsername(), searchUsername.getText().toString()));
                    db.updateUser(searchUser);
                    requestButton.setText("Requested");
                    requestButton.setTextColor(getResources().getColor(R.color.blueLight)); //lb
                    requestButton.setBackgroundColor(getResources().getColor(R.color.blue)); //b
                }else if(requestButton.getText().toString().equals("Requested")){
                    searchUser.removeFollowRequest(new FollowRequest(CurrentUser.get().getUsername(), searchUsername.getText().toString()));
                    db.updateUser(searchUser);

                    requestButton.setText("Request");
                    requestButton.setTextColor(getResources().getColor(R.color.blue));//b
                    requestButton.setBackgroundColor(getResources().getColor(R.color.blueLight)); //lb
                }else{
                    searchUser.removeFollower(CurrentUser.get().getUsername());
                    db.updateUser(searchUser);

                    user.removeFollowing(searchUser.getUsername());
                    db.updateUser(user);

                    requestButton.setText("Request");
                    requestButton.setTextColor(getResources().getColor(R.color.blue));
                    requestButton.setBackgroundColor(getResources().getColor(R.color.blueLight)); //lb
                }
            }
        });

        //initializing navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    /**
                     * Declares action to take when a navigation item is selected.
                     * @param item the item that was selected
                     * @return false if the activity is not started correctly
                     */
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //switching through actions
                        switch (item.getItemId()) {
                            case R.id.action_today:
                                startActivity(intentToday);
                                break;
                            case R.id.action_habits:
                                startActivity(intentHabits);
                                break;
                            case R.id.action_events:
                                startActivity(intentEvents);
                                break;
                            case R.id.action_profile:
                                startActivity(intentProfile);
                                break;

                        }
                        return false;
                    }
                });
    }

}
