package com.example.oldhabitsdiehard;

import android.graphics.Bitmap;
import android.location.Location;

import java.time.LocalDate;

/**
 * This is a Habit Event class
 */

public class HabitEvent {
    Habit habit; // required
    String comment;
    Bitmap image;
    LocalDate date; // required
    Location location; // required


    /**
     * Constructor having all attributes specified
     * @param habit the habit class that this event belongs to
     * @param comment on the Habit event
     * @param image on the Habit event
     * @param date on the Habit event
     * @param location of the Habit event
     */
    HabitEvent(Habit habit, String comment, Bitmap image, LocalDate date, Location location){
        this.habit = habit;
        setComment(comment);
        setImageBmap(image);
        setDate(date);
        setLocation(location);
    }

    /**
     * Constructor without comment specified
     * @param habit the habit class that this event belongs to
     * @param image on the Habit event
     * @param date on the Habit event
     * @param location of the Habit event
     */
    HabitEvent(Habit habit, Bitmap image, LocalDate date, Location location){
        this.habit = habit;
        setImageBmap(image);
        setDate(date);
        setLocation(location);
    }


    /**
     * Constructor without image specified
     * @param habit the habit class that this event belongs to
     * @param comment on the Habit event
     * @param date on the Habit event
     * //@param location of the Habit event
     */
    HabitEvent(Habit habit, String comment, LocalDate date, Location location){
        this.habit = habit;
        setComment(comment);
        setDate(date);
        setLocation(location);
    }

    /**
     * Constructor without comment and image specified
     * @param habit the habit class that this event belongs to
     * @param date on the Habit event
     * @param location of the Habit event
     */
    HabitEvent(Habit habit, LocalDate date, Location location){
        this.habit = habit;
        setDate(date);
        setLocation(location);
    }


    /**
     * Getter for Habit
     * @return the Habit class object that this event belongs to
     */
    public Habit getHabit(){
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
    public LocalDate getDate(){
        return date;
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
    public void setHabit(Habit habit) {
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
     * @param Date for Habit event
     */
    public void setDate(LocalDate Date){
        this.date = date;
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
