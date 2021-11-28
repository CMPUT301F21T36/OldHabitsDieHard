package com.example.oldhabitsdiehard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    private User user;
    private Integer minPasswordLength = 2;

    /**
     * Defines action to take when activity is created.
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        user = CurrentUser.get();
        EditText usernameBox = findViewById(R.id.username_edit);
        EditText bioBox = findViewById(R.id.bio_edit);
        EditText currentPassword = findViewById(R.id.current_password);
        EditText newPassword = findViewById(R.id.password_new_edit);
        EditText confirmPassword = findViewById(R.id.password_confirm_edit);
        Button saveProfileButton = findViewById(R.id.save_profile_edits);
        Button backProfileButton = findViewById(R.id.back_profile);
        Button savePasswordButton = findViewById(R.id.save_password_edit);



        usernameBox.setText(user.getUsername());
        bioBox.setText(user.getBio());

        backProfileButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Defines action to take when the create button is clicked
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Defines action to take when the create button is clicked
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                user.setBio(bioBox.getText().toString());
                user.setUsername(usernameBox.getText().toString());
                Toast.makeText(getApplicationContext(), "Profile updated!", Toast.LENGTH_LONG).show();

            }
        });

        savePasswordButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Defines action to take when the create button is clicked
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                String currentPasswordString = currentPassword.getText().toString();
                String newPasswordString = newPassword.getText().toString();
                String confirmPasswordString = confirmPassword.getText().toString();
                if (currentPasswordString.equals(user.getPassword())) {
                    if (newPasswordString.equals(confirmPasswordString)) {
                        if (currentPasswordString.equals(newPasswordString)) {
                            Toast.makeText(getApplicationContext(), "You cannot set new password to current password.", Toast.LENGTH_LONG).show();
                        } else if (newPasswordString.length() < minPasswordLength) {
                            Toast.makeText(getApplicationContext(), "New password not valid", Toast.LENGTH_LONG).show();
                        } else {
                            user.setPassword(newPasswordString);
                            Toast.makeText(getApplicationContext(), "Password successfully changed!", Toast.LENGTH_LONG).show();
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "New password not confirmed", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Invalid current password", Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
