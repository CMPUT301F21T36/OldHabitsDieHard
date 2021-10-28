package com.example.oldhabitsdiehard;

import android.graphics.Bitmap;
import android.location.Location;

import java.time.LocalDate;

/**
 * This is a Habit Event class
 */

public class HabitEvent {
    Habit habit;
    String comment;
    Bitmap image;
    LocalDate date;
    Location location;


    /**
     * Constructor having all attributes specified
     * @param habit the habit class
     * @param comment on the Habit event
     * @param image on the Habit event
     * @param date on the Habit event
     * @param location of the Habit event
     */
    HabitEvent(Habit habit, String comment, Bitmap image, LocalDate date, Location location){
        this.habit = habit;
        this.setComment(comment);
        this.setImageBmap(image);
        this.setDate(date);
        this.location = location;
    }

    /**
     * Constructor without comment specified
     * @param habit the habit class
     * @param image on the Habit event
     * @param date on the Habit event
     * @param location of the Habit event
     */
    HabitEvent(Habit habit, Bitmap image, LocalDate date, Location location){
        this.habit = habit;
        this.setImageBmap(image);
        this.setDate(date);
        this.location = location;
    }


    /**
     * Constructor without image specified
     * @param habit the habit class
     * @param comment on the Habit event
     * @param date on the Habit event
     * @param location of the Habit event
     */
    HabitEvent(Habit habit, String comment, LocalDate date, Location location){
        this.habit = habit;
        this.setComment(comment);
        this.setDate(date);
        this.location = location;
    }

    /**
     * Constructor without comment and image specified
     * @param habit the habit class
     * @param date on the Habit event
     * @param location of the Habit event
     */
    HabitEvent(Habit habit, LocalDate date, Location location){
        this.habit = habit;
        this.setDate(date);
        this.location = location;
    }


    /**
     * Getter for Habit
     * @return Habit class object
     */
    public Habit getHabit(){
        return this.habit;
    }

    /**
     * Getter for comment
     * @return comment of the Habit event
     */
    public String getComment(){
        return this.comment;
    }

    /**
     * Getter for image
     * @return image of the Habit event
     */
    public Bitmap getImageBmap(){
        return this.image;
    }

    /**
     * Getter for date
     * @return date of Habit event
     */
    public LocalDate getDate(){
        return this.date;
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
     * Method for days since this Habit event is done (not implemented yet)
     * @return the number of days since the event is completed
     */
    public int daysSince(){
        return 0;
    }


}
