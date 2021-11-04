package com.example.oldhabitsdiehard;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class represents a habit.
 */
public class Habit {
    private String title; // required
    private String reason; // required
    private LocalDate startDate; // default today
    private boolean[] weekdays; // default all false
    private ArrayList<HabitEvent> events; // default empty
    private boolean isPublic; // default true

    /**
     * Habit constructor specifying all fields.
     * @param title the habit name
     * @param reason the reason for the habit
     * @param startDate the date to start the habit
     * @param weekdays which days of the week the habit should be performed
     * @param isPublic whether this habit is viewable by other users or not
     */
    Habit(String title, String reason, LocalDate startDate, boolean[] weekdays, boolean isPublic) {
        setTitle(title);
        setReason(reason);
        setStartDate(startDate);
        setWeekdays(weekdays);
        setPublic(isPublic);
        events = new ArrayList<HabitEvent>();
    }

    /**
     * Habit constructor specifying all fields except publicity.
     * @param title the habit name
     * @param reason the reason for the habit
     * @param startDate the date to start the habit
     * @param weekdays which days of the week the habit should be performed
     */
    Habit(String title, String reason, LocalDate startDate, boolean[] weekdays) {
        setTitle(title);
        setReason(reason);
        setStartDate(startDate);
        setWeekdays(weekdays);
        setPublic(true);
        events = new ArrayList<HabitEvent>();
    }

    /**
     * Habit constructor without a date specification
     * @param title the habit name
     * @param reason the reason for the habit
     * @param weekdays which days of the week the habit should be performed
     * @param isPublic whether this habit is viewable by other users or not
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    Habit(String title, String reason, boolean[] weekdays, boolean isPublic) {
        // set the default date to today
        LocalDate defDate = LocalDate.now();
        setTitle(title);
        setReason(reason);
        setStartDate(defDate);
        setWeekdays(weekdays);
        setPublic(isPublic);
        events = new ArrayList<HabitEvent>();
    }

    /**
     * Habit constructor without a date or public specification
     * @param title the habit name
     * @param reason the reason for the habit
     * @param weekdays which days of the week the habit should be performed
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    Habit(String title, String reason, boolean[] weekdays) {
        // set the default date to today
        LocalDate defDate = LocalDate.now();
        setTitle(title);
        setReason(reason);
        setStartDate(defDate);
        setWeekdays(weekdays);
        setPublic(true);
        events = new ArrayList<HabitEvent>();
    }

    /**
     * Habit constructor specifying title, reason, and startDate
     * @param title the name of the habit
     * @param reason the reason for the habit
     * @param startDate the date to start the habit
     */
    Habit(String title, String reason, LocalDate startDate) {
        boolean[] defWeekdays = new boolean[7]; // initialized to false
        setTitle(title);
        setReason(reason);
        setStartDate(startDate);
        setWeekdays(defWeekdays);
        setPublic(true);
        events = new ArrayList<HabitEvent>();
    }

    /**
     * Habit constructor specifying only title and reason
     * @param title the name of the habit
     * @param reason the reason for the habit
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    Habit(String title, String reason) {
        // set default date to today
        LocalDate defDate = LocalDate.now();
        // set default weekdays to false
        boolean[] defWeekdays = new boolean[7];
        setTitle(title);
        setReason(reason);
        setStartDate(defDate);
        setWeekdays(defWeekdays);
        setPublic(true);
        events = new ArrayList<HabitEvent>();
    }

    /**
     * Returns the title of a habit
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the reason for a habit
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns the start date for a habit
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Returns the weekdays on which a habit should be performed
     * @return a boolean array of length 7 starting at Sunday, with value true
     * if the habit is performed on that day, false if not
     */
    public boolean[] getWeekdays() {
        return weekdays;
    }

    /**
     * Return the events associated with this habit
     * @return HabitEventList object
     */
    public ArrayList<HabitEvent> getHabitEvents() { return events; }

    /**
     * Adds a habitEvent to the events list
     * @param habitEvent
     */
    public void addHabitEvent(HabitEvent habitEvent){
        events.add(habitEvent);
    }

    public boolean getPublic() {
        return isPublic;
    }

    /**
     * Sets the title of a habit
     * @param title the title
     */
    public void setTitle(String title) {
        if (title.length() > 20) {
            title = title.substring(0,20);
        }
        this.title = title;
    }

    /**
     * Sets the reason for a habit
     * @param reason the reason
     */
    public void setReason(String reason) {
        if (reason.length() > 30) {
            reason = reason.substring(0,30);
        }
        this.reason = reason;
    }

    /**
     * Sets the start date of a habit
     * @param startDate the start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the weekdays on which a habit should be performed
     * @param weekdays a boolean array of length 7 with value true if the
     *                 habit should be performed on that weekday, false if not;
     *                 weekdays[0] represents Sunday
     */
    public void setWeekdays(boolean[] weekdays) {
        this.weekdays = weekdays;
    }

    /**
     * Sets the publicity of the habit to true or false. If public, a habit may
     * be viewed by other users who follow this user.
     * @param isPublic
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Returns a score representing how well a user is following this habit
     * @return the score
     */
    public int followScore() {
        // calculate the score based on how many days it has been followed
        return 0;
    }

}
