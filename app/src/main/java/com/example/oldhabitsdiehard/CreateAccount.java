package com.example.oldhabitsdiehard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity{
    private User user;
    private Integer minPasswordLength = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //set up edit texts and buttons
        EditText usernameBox = findViewById(R.id.username_create);
        EditText passwordBox = findViewById(R.id.password_create);
        EditText passwordConfirmBox = findViewById(R.id.confirm_password);
        EditText bioBox = findViewById(R.id.bio_create);
        Button createButton = findViewById(R.id.create_account_button);
        Button backLoginButton = findViewById(R.id.back_login);

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
                String passwordConfirm = passwordConfirmBox.getText().toString();
                String bio = bioBox.getText().toString();

                if (username.length() < 1) {
                    //blank username make alert
                    Toast.makeText(getApplicationContext(), "Please enter a valid username", Toast.LENGTH_LONG).show();
                } else if (!password.equals(passwordConfirm)) {
                    // not matching password alert
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();

                } else if (password.length() < minPasswordLength) {
                    //too short password alert
                    Toast.makeText(getApplicationContext(), "Your password is too short and invalid", Toast.LENGTH_LONG).show();

                } else {

                    // create new user
                    user = new User(username, password, bio);

                    // check if we can add it to the database
                    boolean success = db.addUser(user);

                    if (success) {
                        // account was created successfully
                        CurrentUser.set(user);
                        // switch to today view
                        Intent intent = new Intent(view.getContext(), TodayActivity.class);
                        startActivity(intent);
                    } else {
                        // account already exists
                        // account already exists alert
                        Toast.makeText(getApplicationContext(), "Username already exists\nPlease try a different option!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        backLoginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Define action to take when login button is clicked.
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
        });

    }
}
