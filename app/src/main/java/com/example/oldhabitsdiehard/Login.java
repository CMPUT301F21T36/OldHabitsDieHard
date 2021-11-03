package com.example.oldhabitsdiehard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameBox = findViewById(R.id.username_box);
        EditText passwordBox = findViewById(R.id.password_box);
        TextView infoBox = findViewById(R.id.info_box);
        Button createButton = findViewById(R.id.create_button);
        Button loginButton = findViewById(R.id.login_button);

        UserDatabase db = UserDatabase.getInstance();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameBox.getText().toString();
                String password = passwordBox.getText().toString();

                User newUser = new User(username, password);

                boolean success = db.addUser(newUser);

                if (success) {
                    infoBox.setText("Created account");
                }
                else {
                    infoBox.setText("Username already exists");
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameBox.getText().toString();
                String password = passwordBox.getText().toString();

                User user = db.checkLogin(username, password);

                if (user != null) {
                    infoBox.setText("Login success");
                    CurrentUser.set(user);
                }
                else {
                    infoBox.setText("Login fail");
                }
            }
        });
    }
}