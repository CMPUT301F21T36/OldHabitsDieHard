package com.example.oldhabitsdiehard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.android.gms.maps.model.LatLng;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Tests for the Habit class
 */
public class HabitTest {
    private Habit mockHabit() {
        LocalDate myDate = LocalDate.now();
        List<Boolean> myWeekdays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7])); // initialized to false
        Collections.fill(myWeekdays, Boolean.FALSE);
        myWeekdays.set(0, Boolean.TRUE);
        myWeekdays.set(2, Boolean.TRUE);
        myWeekdays.set(4, Boolean.TRUE);
        myWeekdays.set(6, Boolean.TRUE);
        Habit habit = new Habit("Eat breakfast", "Hungry", myDate, myWeekdays);
        return habit;
    }

    @Test
    void testConstructor() {
        LocalDate myDate = LocalDate.of(2021,10,26);
        List<Boolean> myWeekdays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7])); // initialized to false
        Collections.fill(myWeekdays, Boolean.FALSE);
        myWeekdays.set(0, Boolean.TRUE);
        myWeekdays.set(2, Boolean.TRUE);
        myWeekdays.set(6, Boolean.TRUE);
        Habit habit = new Habit("", "", myDate, myWeekdays);
        Habit habit2 = new Habit("", "", myDate);
        Habit habit3 = new Habit("", "", myWeekdays);
        assertEquals("", habit.getTitle());
        assertEquals("", habit.getReason());

        int day = habit.getDay();
        int month = habit.getMonth();
        int year = habit.getYear();
        LocalDate startDate = LocalDate.of(year, month, day);
        assertEquals(LocalDate.of(2021,10,26), startDate);

        day = habit3.getDay();
        month = habit3.getMonth();
        year = habit3.getYear();
        LocalDate startDate3 = LocalDate.of(year, month, day);
        assertEquals(LocalDate.now(), startDate3);

        List<Boolean> testDays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7]));
        Collections.fill(testDays, Boolean.FALSE);
        testDays.set(0, Boolean.TRUE);
        testDays.set(2, Boolean.TRUE);
        testDays.set(6, Boolean.TRUE);
        for (int i = 0; i < 7; i++) {
            assertEquals(testDays.get(i), habit.getWeekdays().get(i));
            assertEquals(false, habit2.getWeekdays().get(i));
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
        assertEquals(LocalDate.now().getYear(), myHabit.getYear());
        assertEquals(LocalDate.now().getMonthValue(), myHabit.getMonth());
        assertEquals(LocalDate.now().getDayOfMonth(), myHabit.getDay());
    }

    @Test
    void testGetWeekdays() {
        Habit myHabit = mockHabit();
        List<Boolean> testDays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7])); // initialized to false
        Collections.fill(testDays, Boolean.FALSE);
        testDays.set(0, Boolean.TRUE);
        testDays.set(2, Boolean.TRUE);
        testDays.set(4, Boolean.TRUE);
        testDays.set(6, Boolean.TRUE);
        for (int i = 0; i < 7; i++) {
            assertEquals(testDays.get(i), myHabit.getWeekdays().get(i));
        }
    }

    @Test
    void testGetPublic() {
        Habit myHabit = mockHabit();
        assertTrue(myHabit.getPublic());
        List<Boolean> testDays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7])); // initialized to false
        Collections.fill(testDays, Boolean.FALSE);

        myHabit = new Habit("test", "test", testDays, false);
        assertFalse(myHabit.getPublic());
    }

    @Test
    void testHabitEvents() {
        Habit myHabit = mockHabit();
        HabitEvent myEvent = new HabitEvent(myHabit.getTitle(), "testComment", LocalDate.now());
        myHabit.addHabitEvent(myEvent);
        assertTrue(myHabit.getHabitEvents().contains(myEvent));
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
        assertEquals(2021, myHabit.getYear());
        assertEquals(10, myHabit.getMonth());
        assertEquals(26, myHabit.getDay());
    }

    @Test
    void testSetWeekdays() {
        Habit myHabit = mockHabit();
        List<Boolean> weekdays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7])); // initialized to false
        Collections.fill(weekdays, Boolean.TRUE);

        myHabit.setWeekdays(weekdays);
        for (int i = 0; i < 7; i++) {
            assertTrue(myHabit.getWeekdays().get(i));
        }
    }

    @Test
    void testSetPublic() {
        Habit myHabit = mockHabit();
        myHabit.setPublic(false);
        assertFalse(myHabit.getPublic());
    }

    @Test
    void testHabitScore() {
        List<Boolean> weekdays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7])); // initialized to false
        Collections.fill(weekdays, Boolean.TRUE);
        Habit myHabit = new Habit("Habit", "Reason", weekdays); // start date today

        // Score 3
        assertEquals(3, myHabit.followScore());

        // Score 2
        myHabit.setStartDate(LocalDate.now().minusDays(1));
        assertEquals(2, myHabit.followScore());

        // Score 1
        myHabit.setStartDate(LocalDate.now().minusDays(2));
        assertEquals(1, myHabit.followScore());

        // Score 0
        myHabit.setStartDate(LocalDate.now().minusDays(3));
        assertEquals(0, myHabit.followScore());
    }
}
