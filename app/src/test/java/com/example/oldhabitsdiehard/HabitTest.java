/*
 *  HabitTest
 *
 *  Version 2.0
 *
 *  November 28, 2021
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Tests for the Habit class.
 *
 * @author Claire Martin
 * @author Rowan Tilroe
 */
public class HabitTest {

    /**
     * Creates a test Habit object for use in the following test cases.
     * @return a Habit
     */
    private Habit mockHabit() {
        // set date to today
        LocalDate myDate = LocalDate.now();

        // initialize weekdays to Sunday, Tuesday, Thursday and Saturday
        List<Boolean> myWeekdays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7])); // initialized to false
        Collections.fill(myWeekdays, Boolean.FALSE);
        myWeekdays.set(0, Boolean.TRUE);
        myWeekdays.set(2, Boolean.TRUE);
        myWeekdays.set(4, Boolean.TRUE);
        myWeekdays.set(6, Boolean.TRUE);

        // create habit
        Habit habit = new Habit("Eat breakfast", "Hungry", myDate, myWeekdays);
        return habit;
    }

    /**
     * Tests the Habit constructor to make sure all fields are set properly, and
     * if fields are not given then their default values are used.
     */
    @Test
    void testConstructor() {
        // set date
        LocalDate myDate = LocalDate.of(2021,10,26);
        // initialize false boolean array
        List<Boolean> myWeekdays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7]));
        Collections.fill(myWeekdays, Boolean.FALSE);
        // set weekdays to sunday, tuesday and saturday
        myWeekdays.set(0, Boolean.TRUE);
        myWeekdays.set(2, Boolean.TRUE);
        myWeekdays.set(6, Boolean.TRUE);

        // create habit with empty title and reason
        Habit habit = new Habit("", "", myDate, myWeekdays);
        // create habit with empty title and reason, and default weekdays
        Habit habit2 = new Habit("", "", myDate);
        // create habit with empty title and reason, and default date
        Habit habit3 = new Habit("", "", myWeekdays);

        // check to make sure habit title and reason are both empty strings
        assertEquals("", habit.getTitle());
        assertEquals("", habit.getReason());

        // check to make sure habit date is correct
        int day = habit.getDay();
        int month = habit.getMonth();
        int year = habit.getYear();
        LocalDate startDate = LocalDate.of(year, month, day);
        assertEquals(LocalDate.of(2021,10,26), startDate);

        // check to make sure default habit date is today
        day = habit3.getDay();
        month = habit3.getMonth();
        year = habit3.getYear();
        LocalDate startDate3 = LocalDate.of(year, month, day);
        assertEquals(LocalDate.now(), startDate3);

        // check to make sure default habit weekdays are false
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

    /**
     * Tests the getTitle() method of the habit class. Check to make sure the
     * test habit's title is correct.
     */
    @Test
    void testGetTitle() {
        Habit myHabit = mockHabit();
        assertEquals("Eat breakfast", myHabit.getTitle());
    }

    /**
     * Tests the getReason() method of the habit class. Check to make sure the
     * test habit's reason is correct.
     */
    @Test
    void testGetReason() {
        Habit myHabit = mockHabit();
        assertEquals("Hungry", myHabit.getReason());
    }

    /**
     * Tests the getDay(), getMonth() and getYear() methods of the habit class.
     * Checks to make sure the test habit's date values are correct
     */
    @Test
    void testGetStartDate() {
        Habit myHabit = mockHabit();
        assertEquals(LocalDate.now().getYear(), myHabit.getYear());
        assertEquals(LocalDate.now().getMonthValue(), myHabit.getMonth());
        assertEquals(LocalDate.now().getDayOfMonth(), myHabit.getDay());
    }

    /**
     * Tests the getWeekdays() method of the habit class. Checks to make sure
     * the test habit's weekday values are correct.
     */
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

    /**
     * Tests the getPublic() method of the habit class. Checks to make sure the
     * test habit's public value is correct.
     */
    @Test
    void testGetPublic() {
        // check to make sure mock habit's public value is true
        Habit myHabit = mockHabit();
        assertTrue(myHabit.getPublic());

        // initialize false array
        List<Boolean> testDays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7]));
        Collections.fill(testDays, Boolean.FALSE);

        // check to make sure new habit's public value is false
        myHabit = new Habit("test", "test", testDays, false);
        assertFalse(myHabit.getPublic());
    }

    /**
     * Tests the getHabitEvents() method of the habit class. Adds a habit event
     * to the test habit, then checks to make sure it is stored properly.
     */
    @Test
    void testHabitEvents() {
        Habit myHabit = mockHabit();
        // add a habit event to the habit
        HabitEvent myEvent = new HabitEvent(myHabit.getTitle(), "testComment", LocalDate.now());
        myHabit.addHabitEvent(myEvent);
        // check to make sure it is in the habit events list
        assertTrue(myHabit.getHabitEvents().contains(myEvent));
    }

    /**
     * Tests the setTitle() method of the habit class. Changes the habit title
     * and checks ot make sure it is updated properly.
     */
    @Test
    void testSetTitle() {
        Habit myHabit = mockHabit();

        // change the title of the habit
        myHabit.setTitle("Yoga");
        assertEquals("Yoga", myHabit.getTitle());

        // test title longer than 20
        myHabit.setTitle("this is longer than 20 characters");
        assertEquals("this is longer than ", myHabit.getTitle());
    }

    /**
     * Tests the setReason() method of the habit class. Changes the reason
     * of the test habit and checks to make sure it is updated properly.
     */
    @Test
    void testSetReason() {
        Habit myHabit = mockHabit();

        // change the title of the habit
        myHabit.setReason("Exercise");
        assertEquals("Exercise", myHabit.getReason());

        // test reason longer than 30
        myHabit.setReason("this reason is much much longer than 30 characters");
        assertEquals("this reason is much much longe", myHabit.getReason());
    }

    /**
     * Tests the setStartDate() method of the habit class. Changes the date of the
     * test habit and checks ot make sure it is updated properly.
     */
    @Test
    void testSetStartDate() {
        Habit myHabit = mockHabit();
        myHabit.setStartDate(LocalDate.of(2021,10,26));
        assertEquals(2021, myHabit.getYear());
        assertEquals(10, myHabit.getMonth());
        assertEquals(26, myHabit.getDay());
    }

    /**
     * Tests the setWeekdays() method of the habit class. Changes the weekdays
     * of the test habit and checks to make sure they are updated properly.
     */
    @Test
    void testSetWeekdays() {
        Habit myHabit = mockHabit();
        List<Boolean> weekdays = new ArrayList<Boolean>(Arrays.asList(new Boolean[7])); // initialized to false
        Collections.fill(weekdays, Boolean.TRUE);

        // all habit weekdays should be true
        myHabit.setWeekdays(weekdays);
        for (int i = 0; i < 7; i++) {
            assertTrue(myHabit.getWeekdays().get(i));
        }
    }

    /**
     * Tests the setPublic() method of the habit class. Changes the habit
     * publicity to false, then checks to make sure it was updated properly.
     */
    @Test
    void testSetPublic() {
        Habit myHabit = mockHabit();
        myHabit.setPublic(false);
        assertFalse(myHabit.getPublic());
    }

    /**
     * Tests the followScore() method of the habit class. Creates a fake habit,
     * then makes sure the score is accurate depending on which day the habit
     * was supposed to start.
     */
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
