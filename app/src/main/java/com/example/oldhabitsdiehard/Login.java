/*
 *  Login
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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class defines the Login activity which is called when the app starts.
 *
 * @author Rowan Tilroe
 */
public class Login extends AppCompatActivity {
    private User user;

    /**
     * Defines action to take when the activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set up boxes for user to enter login info
        EditText usernameBox = findViewById(R.id.username_box);
        EditText passwordBox = findViewById(R.id.password_box);
        TextView infoBox = findViewById(R.id.info_box);
        Button createButton = findViewById(R.id.create_button);
        Button loginButton = findViewById(R.id.login_button);

        // get instance of database
        UserDatabase db = UserDatabase.getInstance();

        // define create account button
        createButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Defines action to take when the create button is clicked
             * @param view
             */
            @Override
            public void onClick(View view) {
                // get entered username and password
                String username = usernameBox.getText().toString();
                String password = passwordBox.getText().toString();

                // empty box check
                if (username.length() < 1 || password.length() < 1) {
                    return;
                }

                // create new user
                user = new User(username, password);

                // check if we can add it to the database
                boolean success = db.addUser(user);

                if (success) {
                    // account was created successfully
                    infoBox.setText("Account Created!");
                    infoBox.setVisibility(View.VISIBLE);
                    CurrentUser.set(user);
                    // swithc to today view
                    Intent intent = new Intent(view.getContext(), TodayActivity.class);
                    startActivity(intent);
                }
                else {
                    // account already exists
                    infoBox.setText("Username already exists");
                    infoBox.setVisibility(View.VISIBLE);
                    usernameBox.setText("");
                    passwordBox.setText("");
                }
            }
        });

        // define login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Define action to take when login button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                // get entered username and password
                String username = usernameBox.getText().toString();
                String password = passwordBox.getText().toString();

                // empty box check
                if (username.length() < 1 || password.length() < 1) {
                    return;
                }

                // check whether the user info is correct
                user = db.checkLogin(username, password);

                if (user != null) {
                    // login success
                    CurrentUser.set(user);
                    // start today view
                    Intent intent = new Intent(view.getContext(), TodayActivity.class);
                    startActivity(intent);
                } else {
                    // login failure
                    infoBox.setText("Incorrect Login");
                    usernameBox.setText("");
                    passwordBox.setText("");
                }
            }
        });
    }
}