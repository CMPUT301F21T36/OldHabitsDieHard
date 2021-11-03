package com.example.oldhabitsdiehard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Tests for the Habit class
 */
public class HabitTest {
    private Habit mockHabit() {
        LocalDate myDate = LocalDate.of(2021,1,1);
        boolean[] myWeekdays = new boolean[7];
        myWeekdays[0] = true;
        myWeekdays[2] = true;
        myWeekdays[4] = true;
        myWeekdays[6] = true;
        Habit habit = new Habit("Eat breakfast", "Hungry", myDate, myWeekdays);
        return habit;
    }

    @Test
    void testConstructor() {
        LocalDate myDate = LocalDate.of(2021,10,26);
        boolean[] myWeekdays = new boolean[7];
        myWeekdays[0] = true;
        myWeekdays[2] = true;
        myWeekdays[6] = true;
        Habit habit = new Habit("", "", myDate, myWeekdays);
        Habit habit2 = new Habit("", "", myDate);
        Habit habit3 = new Habit("", "", myWeekdays);
        assertEquals("", habit.getTitle());
        assertEquals("", habit.getReason());
        assertEquals(LocalDate.of(2021,10,26), habit.getStartDate());
        assertEquals(LocalDate.now(), habit3.getStartDate());
        boolean testDays[] = {true, false, true, false, false, false, true};
        for (int i = 0; i < 7; i++) {
            assertEquals(testDays[i], habit.getWeekdays()[i]);
            assertEquals(false, habit2.getWeekdays()[i]);
        }
    }

    @Test
    void testGetTitle() {
        Habit myHabit = mockHabit();
        assertEquals("Eat breakfast", myHabit.getTitle());
    }

    @Test
    void testGetReason() {
        Habit myHabit = mockHabit();
        assertEquals("Hungry", myHabit.getReason());
    }

    @Test
    void testGetStartDate() {
        Habit myHabit = mockHabit();
        assertEquals(LocalDate.of(2021, 1,1), myHabit.getStartDate());
    }

    @Test
    void testGetWeekdays() {
        Habit myHabit = mockHabit();
        boolean testDays[] = {true, false, true, false, true, false, true};
        for (int i = 0; i < 7; i++) {
            assertEquals(testDays[i], myHabit.getWeekdays()[i]);
        }
    }

    @Test
    void testSetTitle() {
        Habit myHabit = mockHabit();
        myHabit.setTitle("Yoga");
        assertEquals("Yoga", myHabit.getTitle());

        // test title longer than 20
        myHabit.setTitle("this is longer than 20 characters");
        assertEquals("this is longer than ", myHabit.getTitle());
    }

    @Test
    void testSetReason() {
        Habit myHabit = mockHabit();
        myHabit.setReason("Exercise");
        assertEquals("Exercise", myHabit.getReason());

        // test reason longer than 30
        myHabit.setReason("this reason is much much longer than 30 characters");
        assertEquals("this reason is much much longe", myHabit.getReason());
    }

    @Test
    void testSetDate() {
        Habit myHabit = mockHabit();
        myHabit.setStartDate(LocalDate.of(2021,10,26));
        assertEquals(LocalDate.of(2021,10,26), myHabit.getStartDate());
    }

    @Test
    void testSetWeekdays() {
        Habit myHabit = mockHabit();
        myHabit.setWeekdays(new boolean[]{true, true, true, true, true, true, true});
        boolean testDays[] = {true, true, true, true, true, true, true};
        for (int i = 0; i < 7; i++) {
            assertEquals(testDays[i], myHabit.getWeekdays()[i]);
        }
    }
}
