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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

/**
 * This class defines the Login activity which is called when the app starts.
 *
 * @author Rowan Tilroe
 */
public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private User user;
    SignInButton signInButton;
    private  GoogleApiClient googleApiClient;
    private static final int SIGN_IN=1;

    /**
     * Defines action to take when the activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton=(SignInButton)findViewById(R.id.google_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN);
            }
        });


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
                if (username.length() < 1) {
                    usernameBox.setError("Username Required");
                    if(password.length() < 1){
                        passwordBox.setError("Password Required");
                        return;
                    }
                    return;
                }
                if(password.length() < 1){
                    passwordBox.setError("Password Required");
                    if(username.length() < 1){
                        usernameBox.setError("username Required");
                        return;
                    }
                    return;
                }


                boolean lower = false;
                boolean upper = false;
                boolean number = false;
                boolean specialCharacter = false;
                boolean length = false;
                char passwordChar;
                for(int i=0; i<password.length();i++){
                    passwordChar = password.charAt(i);
                    if(Character.isUpperCase(passwordChar)){
                        upper = true;
                    } else if(Character.isLowerCase(passwordChar)){
                        lower = true;
                    } else if(Character.isDigit(passwordChar)){
                        number = true;
                    } else{
                        specialCharacter = true;
                    }
                }
                if(password.length() >= 8){
                    length=true;
                }

                if(!(length && upper && number && lower && specialCharacter)){
                    passwordBox.setError("Password must be at least 8 character long with at least one upper letter, lower letter, digit, and special character.");
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
                    infoBox.setVisibility(View.VISIBLE);
                    usernameBox.setText("");
                    passwordBox.setText("");
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }



    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            // get instance of database
            UserDatabase db = UserDatabase.getInstance();
            // check whether the user info is correct

            String username = account.getEmail();
            String password = account.getId();

            user = db.checkLogin(username, password);

            if (user != null) {
                // login success
                CurrentUser.set(user);
                // start today view
                Intent intent = new Intent(this, TodayActivity.class);
                startActivity(intent);
            } else {
                user = new User(username, password);
                // check if we can add it to the database
                boolean success = db.addUser(user);
                if (success) {

                    CurrentUser.set(user);
                    // swithc to today view
                    Intent intent = new Intent(this, TodayActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}