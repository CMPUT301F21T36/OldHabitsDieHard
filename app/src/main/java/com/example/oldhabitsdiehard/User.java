package com.example.oldhabitsdiehard;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class represents a user
 * @author Rowan Tilroe
 */
public class User {
    private String username;
    private String password;
    private String bio;
    private ArrayList<Habit> habits;
    private ArrayList<HabitEvent> habitEvents;


    /**
     * User constructor
     * @param username User's username (for login)
     * @param password User's password (for login)
     */
    public User(String username, String password) throws IllegalArgumentException {
        setUsername(username);
        setPassword(password);
        setBio("");
        habits = new ArrayList<Habit>();
        habitEvents = new ArrayList<HabitEvent>();
    }
    public User() {}

    /**
     * Username getter
     * @return User's username
     */
    public String getUsername() { return username; }

    /**
     * Password getter
     * @return User's password
     */
    public String getPassword() { return password; }

    /**
     * Habits getter
     * @return User's HabitList
     */
    public ArrayList<Habit> getHabits() { return habits; }

    public ArrayList<HabitEvent> getHabitEvents() {
        return habitEvents;
    }
    /**
     * Bio getter
     * @return User's bio
     */
    public String getBio() { return bio; }

    /**
     * Username setter
     * @param username
     * @throws IllegalArgumentException
     */
    public void setUsername(String username) throws IllegalArgumentException {
        if (username.length() < 1) {
            throw new IllegalArgumentException();
        }
        else {
            this.username = username;
        }
    }

    /**
     * Password setter
     * @param password
     * @throws IllegalArgumentException
     */
    public void setPassword(String password) throws IllegalArgumentException {
        if (password.length() < 1) {
            throw new IllegalArgumentException();
        }
        else {
            this.password = password;
        }
    }

    /**
     * Bio setter
     * @param bio
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Method to add a habit to this user's habit list.
     * @param habit
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public void addHabitEvent(HabitEvent event) {
        String habitName = event.getHabit();
        for (int i = 0; i < habits.size(); i++) {
            Habit curr = habits.get(i);
            if (curr.getTitle().equals(habitName)) {
                // found the habit
                curr.addHabitEvent(event);
                habitEvents.add(event);
            }
        }
    }

    public void deleteHabit(Habit habit) {
        habits.remove(habit);
        for (int i = 0; i < habitEvents.size(); i++) {
            if (habitEvents.get(i).getHabit().equals(habit.getTitle())) {
                habitEvents.remove(i);
            }
        }
    }

    /**
     * Method to get the habits to be done today from this user's habit list.
     * The list is recalculated every time this method is called so that it is
     * always updated with the correct date. Use this method to create the
     * Today page.
     * @return an ArrayList of the habits to be done tdoay
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Habit> getTodayHabits() {

        ArrayList<Habit> todayHabits = new ArrayList<Habit>();
        // 1 is Monday, 7 is Sunday
        int today = LocalDate.now().getDayOfWeek().getValue();
        if (today == 7) {
            // it's sunday, switch to 0 to match our notation
            today = 0;
        }
        for (int i = 0; i < habits.size(); i++) {
            // iterate through habits in list for this user
            if (habits.get(i).getWeekdays().get(today)) {
                // habit i is performed on this day
                todayHabits.add(habits.get(i));
            }
        }
        return todayHabits;
    }
}
