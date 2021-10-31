package com.example.oldhabitsdiehard;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

/**
 * This class represents a habit.
 */
public class Habit {
    private String title;
    private String reason;
    private LocalDate startDate;
    private boolean[] weekdays;
    private HabitEventList events;

    /**
     * Habit constructor specifying all fields.
     * @param title the habit name
     * @param reason the reason for the habit
     * @param startDate the date to start the habit
     * @param weekdays which days of the week the habit should be performed
     */
    Habit(String title, String reason, LocalDate startDate, boolean[] weekdays) {
        this.setTitle(title);
        this.setReason(reason);
        this.setStartDate(startDate);
        this.setWeekdays(weekdays);
        events = new HabitEventList();
    }

    /**
     * Habit constructor without a date specification
     * @param title the habit name
     * @param reason the reason for the habit
     * @param weekdays which days of the week the habit should be performed
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    Habit(String title, String reason, boolean[] weekdays) {
        // set the default date to today
        LocalDate defDate = LocalDate.now();
        this.setTitle(title);
        this.setReason(reason);
        this.setStartDate(defDate);
        this.setWeekdays(weekdays);
        events = new HabitEventList();
    }

    Habit(String title, String reason, LocalDate startDate) {
        boolean[] defWeekdays = new boolean[7];
        this.setTitle(title);
        this.setReason(reason);
        this.setStartDate(startDate);
        this.setWeekdays(defWeekdays);
        events = new HabitEventList();
    }

    /**
     * Habit constructor without a date or weekdays specification
     * @param title the name of the habit
     * @param reason the reason for the habit
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    Habit(String title, String reason) {
        // set default date to today
        LocalDate defDate = LocalDate.now();
        // set default weekdays to false
        boolean[] defWeekdays = new boolean[7];
        this.setTitle(title);
        this.setReason(reason);
        this.setStartDate(defDate);
        this.setWeekdays(defWeekdays);
        events = new HabitEventList();
    }

    /**
     * Returns the title of a habit
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the reason for a habit
     * @return the reason
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * Returns the start date for a habit
     * @return the start date
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Returns the weekdays on which a habit should be performed
     * @return a boolean array of length 7 starting at Sunday, with value true
     * if the habit is performed on that day, false if not
     */
    public boolean[] getWeekdays() {
        return this.weekdays;
    }

    /**
     * Return the events associated with this habit
     * @return HabitEventList object
     */
    public HabitEventList getHabitEvents() { return this.events; }

    /**
     * Sets the title of a habit
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the reason for a habit
     * @param reason the reason
     */
    public void setReason(String reason) {
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
     * Returns a score representing how well a user is following this habit
     * @return the score
     */
    public int followScore() {
        // calculate the score based on how many days it has been followed
        return 0;
    }

}
