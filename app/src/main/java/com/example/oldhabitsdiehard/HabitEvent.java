package com.example.oldhabitsdiehard;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This is a Habit Event class
 */

public class HabitEvent implements Serializable {
    //private Habit habit; // required
    private String habit;
    private String comment;
    private Bitmap image;
    //private LocalDate date; // required
    private int day;
    private int month;
    private int year;
    private Location location; // required

    public HabitEvent() {}
    /**
     * Constructor having all attributes specified
     * @param habit the habit class that this event belongs to
     * @param comment on the Habit event
     * @param image on the Habit event
     * @param date on the Habit event
     * @param location of the Habit event
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    HabitEvent(String habit, String comment, Bitmap image, LocalDate date, Location location){
        setHabit(habit);
        setComment(comment);
        setImageBmap(image);
        setDate(date);
        setLocation(location);
        //this.habit.addHabitEvent(this);
    }

    /**
     * Constructor without comment specified
     * @param habit the habit class that this event belongs to
     * @param image on the Habit event
     * @param date on the Habit event
     * @param location of the Habit event
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    HabitEvent(String habit, Bitmap image, LocalDate date, Location location){
        setHabit(habit);
        setImageBmap(image);
        setDate(date);
        setLocation(location);
        //this.habit.addHabitEvent(this);
    }


    /**
     * Constructor without image specified
     * @param habit the habit class that this event belongs to
     * @param comment on the Habit event
     * @param date on the Habit event
     * //@param location of the Habit event
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    HabitEvent(String habit, String comment, LocalDate date, Location location){
        setHabit(habit);
        setComment(comment);
        setDate(date);
        setLocation(location);
        //this.habit.addHabitEvent(this);
    }

    /**
     * Constructor without comment and image specified
     * @param habit the habit class that this event belongs to
     * @param date on the Habit event
     * @param location of the Habit event
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    HabitEvent(String habit, LocalDate date, Location location){
        setHabit(habit);
        setDate(date);
        setLocation(location);
        //this.habit.addHabitEvent(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    HabitEvent(String habit, String comment, LocalDate date) {
        setHabit(habit);
        setDate(date);
        setComment(comment);
        //this.habit.addHabitEvent(this);
    }


    /**
     * Getter for Habit
     * @return the Habit class object that this event belongs to
     */
    public String getHabit(){
        return habit;
    }

    /**
     * Getter for comment
     * @return comment of the Habit event
     */
    public String getComment(){
        return comment;
    }

    /**
     * Getter for image
     * @return image of the Habit event
     */
    public Bitmap getImageBmap(){
        return image;
    }

    /**
     * Getter for date
     * @return date of Habit event
     */
    //public LocalDate getDate(){
        //return date;
    //}

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    /**
     * Getter for location
     * @return location of Habit event
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Setter for habit
     * @param habit the Habit object this event belongs to
     */
    public void setHabit(String habit) {
        this.habit = habit;
    }

    /**
     * Setter for comment
     * @param comment for Habit event
     */
    public void setComment(String comment){
        this.comment = comment;
    }

    /**
     * Setter for image
     * @param image for Habit event
     */
    public void setImageBmap(Bitmap image){
        this.image = image;
    }

    /**
     * Setter for date
     * @param date for Habit event
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDate(LocalDate date){
        setDay(date.getDayOfMonth());
        setMonth(date.getMonthValue());
        setYear(date.getYear());
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Setter for location
     * @param location where this habit event occurred
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Method for days since this Habit event is done (not implemented yet)
     * @return the number of days since the event is completed
     */
    public int daysSince(){
        return 0;
    }


}
