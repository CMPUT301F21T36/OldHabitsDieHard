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
import static android.content.Context.LOCATION_SERVICE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.content.pm.PermissionInfoCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
public class HabitEventFragment extends DialogFragment implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMapClickListener {
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
    private Button addLocationButton;
    private Button removeLocationButton;
    private TextView locationText;
    private MyMapView mapView;
    private Marker chosenLocation;
    private boolean isLocationSaved;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap myGoogleMap;
    public LatLng markerLatLng;
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
        user = CurrentUser.get();
        db = UserDatabase.getInstance();
        db.updateUser(user);
        super.onAttach(context);
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
    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // get the current user
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
        addLocationButton = view.findViewById(R.id.addLocationButton);
        removeLocationButton = view.findViewById(R.id.removeLocationButton);
        locationText = view.findViewById(R.id.locationText);

        // default not saving location
        isLocationSaved = false;
        markerLatLng = null;

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        // create a list of habits to populate the spinner
        // the user can only select habits which are already part of the current user
        ArrayList < Habit > habits = user.getHabits();
        ArrayList<String> habitNames = new ArrayList<String>();
        for (int i = 0; i < habits.size(); i++) {
            habitNames.add(habits.get(i).getTitle());
        }
        // create an adapter to describe how the habitNames are displayed
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, habitNames);
        habitEventType.setAdapter(adapter);

        uploadButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        addLocationButton.setOnClickListener(this);
        removeLocationButton.setOnClickListener(this);

        // build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments() != null) {
            // we are editing a habitEvent
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
            locationText.setText("Location is saved!");
            if (myEvent.getHasLocation()) {
                // we have a location
                double lat = myEvent.getLat();
                double lon = myEvent.getLon();
                markerLatLng = new LatLng(lat, lon);
            }
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
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        /**
                         * Defines action to take when delete button is pressed
                         * @param dialogInterface the dialog interface
                         * @param i
                         */
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // create reference for this image
                            String refString = myEvent.getImage();
                            if (refString != null) {
                                StorageReference imgRef = storageRef.child(refString);
                                imgRef.delete();
                            }
                            listener.deleteHabitEvent(myEvent);
                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // get the habit name
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

                            // get image if it exists
                            if (chosenLocation != null && isLocationSaved) {
                                // we have chosen a location and saved it
                                LatLng eventLoc = chosenLocation.getPosition();
                                myEvent.setLocation(eventLoc);
                            }
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
                                        //listener.addHabitEvent(new HabitEvent(habitName, comment, date));
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // add the habit event
                                        myEvent.setImage(refString);
                                        //listener.addHabitEvent(new HabitEvent(habitName, comment, date, refString));
                                    }
                                });
                            }

                            // update event with new info

                            myEvent.setHabit(habitName);
                            myEvent.setComment(comment);
                            myEvent.setDate(date);

                            // add the habit event to the listener
                            listener.editHabitEvent(myEvent);
                        }
                    }).create();
        } else {
            // we are adding a habit event
            return builder
                    .setView(view)
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

                            HabitEvent newEvent = new HabitEvent(habitName, comment, date);

                            if (chosenLocation != null && isLocationSaved) {
                                // we have chosen a location and saved it
                                LatLng eventLoc = chosenLocation.getPosition();
                                newEvent.setLocation(eventLoc);
                            }
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
                                        //listener.addHabitEvent(new HabitEvent(habitName, comment, date));
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // add the habit event
                                        newEvent.setImage(refString);
                                        //listener.addHabitEvent(new HabitEvent(habitName, comment, date, refString));
                                    }
                                });
                            }

                            listener.addHabitEvent(newEvent);
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
        if (view.getId() == R.id.UploadBtn) {
            // we are uploading a photo
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        } else if (view.getId() == R.id.TakePhotoButton) {
            // we are taking a photo
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                // error
            }
        } else if (view.getId() == R.id.addLocationButton) {
            // we want to save our location
            isLocationSaved = true;
            locationText.setText("Location is saved!");
        } else if (view.getId() == R.id.removeLocationButton) {
                // we don't want to save our location
                isLocationSaved = false;
                locationText.setText("Location is not saved!");
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        myGoogleMap = map;
        myGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        //myGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            myGoogleMap.setMyLocationEnabled(true);
            if (markerLatLng != null) {
                // we have clicked a habit event that already has latlng
                myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, 6));
                if (chosenLocation != null) {
                    chosenLocation.remove();
                    chosenLocation = null;
                }
                chosenLocation = myGoogleMap.addMarker(
                        new MarkerOptions()
                                .position(markerLatLng)
                                .title("Marker")
                                .draggable(false)
                );
            }
            myGoogleMap.setOnMyLocationButtonClickListener(this);
            myGoogleMap.setOnMapClickListener(this);
        } else {
            // get permission
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            // try again
            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                myGoogleMap.setMyLocationEnabled(true);
                if (markerLatLng != null) {
                    // we have clicked a habit event that already has latlng
                    myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, 6));
                    if (chosenLocation != null) {
                        chosenLocation.remove();
                        chosenLocation = null;
                    }
                    chosenLocation = myGoogleMap.addMarker(
                            new MarkerOptions()
                                    .position(markerLatLng)
                                    .title("Marker")
                                    .draggable(false)
                    );
                }
                myGoogleMap.setOnMyLocationButtonClickListener(this);
                myGoogleMap.setOnMapClickListener(this);
            } else {
                // user denied permission
            }
        }


        //myGoogleMap.getUiSettings().setScrollGesturesEnabled(false);
        //myGoogleMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapClick(LatLng point) {
        if (chosenLocation != null) {
            chosenLocation.remove();
            chosenLocation = null;
        }
        chosenLocation = myGoogleMap.addMarker(
                new MarkerOptions()
                        .position(point)
                        .title("Marker")
                        .draggable(false)
        );
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}