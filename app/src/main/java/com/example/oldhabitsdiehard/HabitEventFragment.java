/*
 *  HabitEventFragment
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

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A class for the fragment allowing the user to add, edit, view or delete
 * habit events.
 * @author Gurbani Baweja
 * @author Claire Martin
 */
public class HabitEventFragment extends DialogFragment implements View.OnClickListener {
    private User user;
    private UserDatabase db;
    private String currentPhotoPath;
    private Spinner habitEventType;
    private EditText habitEventComment;
    private DatePicker habitEventDate;
    private HabitEventFragment.onFragmentInteractionListener listener;
    private Button uploadButton;
    private Button cameraButton;
    private ImageView img;
    static final int REQUEST_IMAGE_GET = 1;
    static final int REQUEST_IMAGE_CAPTURE = 2;

    /**
     * A listener interface for this fragment to interact with the calling activity.
     */
    public interface onFragmentInteractionListener {
        // abstract methods to be implemented in the activity classes
        void addHabitEvent(HabitEvent event);
        void editHabitEvent(HabitEvent event); // implemented later
        void deleteHabitEvent(HabitEvent event); // implemented later
    }

    /**
     * Creates an instance of the HabitEventFragment.
     * @param habitEvent the habit event we are looking at
     * @return the fragment
     */
    public static HabitEventFragment newInstance(HabitEvent habitEvent) {
        Bundle args = new Bundle();
        args.putSerializable("HabitEvent", habitEvent);

        HabitEventFragment fragment = new HabitEventFragment();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Defines action to take when fragment is attached.
     * @param context the context of the current activity
     */
    @Override
    public void onAttach(Context context){

        super.onAttach(context);
        user = CurrentUser.get();
        db = UserDatabase.getInstance();
        db.updateUser(user);
        if(context instanceof HabitEventFragment.onFragmentInteractionListener){
            listener = (HabitEventFragment.onFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString()+"must implement OnFragmentInteractionListner");
        }
    }

    /**
     * Defines action to take when the fragment is created
     * @param savedInstanceState
     * @return the dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // get the current user
        //user = CurrentUser.get();
        //db = UserDatabase.getInstance();
        db.updateUser(user);
        // get storage reference
        StorageReference storageRef = db.getStorageRef();

        // set the view
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.habit_event_fragment, null);

        // get view for habit event info objects
        habitEventType = view.findViewById(R.id.habitEventType);
        habitEventComment = view.findViewById(R.id.habitEventComment);
        habitEventDate = view.findViewById(R.id.habitEventDate);
        //Spinner dropdown = view.findViewById(R.id.habitEventType);
        uploadButton = view.findViewById(R.id.UploadBtn);
        cameraButton = view.findViewById(R.id.TakePhotoButton);
        img = view.findViewById(R.id.habitEventImage);


        // create a list of habits to populate the spinner
        // the user can only select habits which are already part of the current user
        ArrayList<Habit> habits = user.getHabits();
        ArrayList<String> habitNames = new ArrayList<String>();
        for (int i = 0; i < habits.size(); i++) {
            habitNames.add(habits.get(i).getTitle());
        }
        // create an adapter to describe how the habitNames are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, habitNames);
        habitEventType.setAdapter(adapter);

        uploadButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        // build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments() != null) {
            // we are viewing a habitEvent
            HabitEvent myEvent = (HabitEvent) getArguments().getSerializable("HabitEvent");
            habitEventComment.setText(myEvent.getComment());

            // set which habit type is selected
            String title = myEvent.getHabit();
            int spinnerPos = adapter.getPosition(title);
            habitEventType.setSelection(spinnerPos);

            // get the date info
            int year = myEvent.getYear();
            int month = myEvent.getMonth();
            int day = myEvent.getDay();
            habitEventDate.updateDate(year, month, day);

            String imgString = myEvent.getImage();
            if (imgString != null) {
                StorageReference imgRef = storageRef.child(imgString);
                final long ONE_MEGABYTE = 1024 * 1024;
                imgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap imgBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img.setImageBitmap(imgBitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //not sure what to do here lol
                    }
                });

            }
            return builder
                    .setView(view)
                    .setNegativeButton("Cancel", null)
                    .create();
                    // the following has not been implemented yet and will be ready for Part 4
                    //.setNeutralButton("Delete", new DialogInterface.OnClickListener() {     //For future coding purpose
                        //@RequiresApi(api = Build.VERSION_CODES.O)
                        //@Override
                        //public void onClick(DialogInterface dialogInterface, int i) {
                            // to be implemented later
                        //}
                    //})
                    //.setPositiveButton("Edit", new DialogInterface.OnClickListener() {     //For future coding purpose
                        //@RequiresApi(api = Build.VERSION_CODES.O)
                        //@Override
                        //public void onClick(DialogInterface dialogInterface, int i) {
                            // to be implemented later
                        //}

        } else {
            // we are adding a habit event
            return builder
                    .setView(view)
                    .setTitle("Add Habit")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        /**
                         * Defines action to take when the OK button is pressed.
                         * @param dialogInterface the dialog interface
                         * @param i
                         */
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // get the selected habit name
                            int pos = habitEventType.getSelectedItemPosition();
                            Habit habit = habits.get(pos);
                            String habitName = habit.getTitle();

                            // get the comment for the habit event
                            String comment = habitEventComment.getText().toString();

                            // get the date info for the habit event
                            int day = habitEventDate.getDayOfMonth();
                            int month = habitEventDate.getMonth();
                            int year = habitEventDate.getYear();
                            LocalDate date = LocalDate.of(year, month, day);

                            if (img.getDrawable() != null) {
                                // get img if there is one
                                Bitmap imgBitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                                // get reference to storage bucket
                                //StorageReference storageRef = db.getStorageRef();
                                // generate random uuid to reference image
                                UUID uuid = UUID.randomUUID();
                                String uuidStr = uuid.toString();
                                // create reference for this image
                                String refString = user.getUsername() + "/" + uuidStr + ".jpg";
                                StorageReference imgRef = storageRef.child(refString);
                                // convert image to byte array
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] data = baos.toByteArray();

                                // upload image to storage
                                UploadTask uploadTask = imgRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // add habit event without the image
                                        listener.addHabitEvent(new HabitEvent(habitName, comment, date));
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // add the habit event
                                        listener.addHabitEvent(new HabitEvent(habitName, comment, date, refString));
                                    }
                                });
                            } else {
                                // add the habit event to the listener
                                listener.addHabitEvent(new HabitEvent(habitName, comment, date));
                            }
                        }
                    }).create();
        }
    }

    /**
     * Defines action to take when upload button is clicked.
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.UploadBtn:
                // we are uploading a photo
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_GET);
            case R.id.TakePhotoButton:
                // we are taking a photo
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    // error
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri imgUri = data.getData();
            try {
                img.setImageBitmap(ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContext().getContentResolver(), imgUri)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
        }
    }
}