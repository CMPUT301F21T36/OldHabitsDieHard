/*
 *  HabitEventTest
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

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Testing for HabitEvent class.
 *
 * @author Filippo Ciandy
 * @author Rowan Tilroe
 * @author Claire Martin
 */
public class HabitEventTest {

    /**
     * Creates a habit event object for use in following tests.
     * @return a habit event
     */
    private HabitEvent TestHabitEvent(){
        String habitTitle = "Exercise";
        LocalDate myDate = LocalDate.of(2021,1,1);
        HabitEvent habitevent = new HabitEvent(habitTitle, "at the gym","myImage", myDate,new LatLng(0, 0));
        return habitevent;
    }

    /**
     * Tests the habit event constructor to make sure it returns a valid
     * habit event object.
     */
    @Test
    void TestConstructor(){
        LocalDate myDate = LocalDate.of(2021,1,1);
        HabitEvent habitevent = TestHabitEvent();
        Location location = new Location(LocationManager.PASSIVE_PROVIDER);
        assertEquals("Exercise",habitevent.getHabit());
        assertEquals(myDate,LocalDate.of(habitevent.getYear(),habitevent.getMonth(),habitevent.getDay()));
        assertEquals("at the gym",habitevent.getComment());
        assertEquals("myImage",habitevent.getImage());
        assertTrue(habitevent.getHasLocation());
    }

    /**
     * Tests the getHabit() method of the HabitEvent class. Gets the habit name
     * and checks to make sure it is correct.
     */
    @Test
    void TestGetHabit(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals("Exercise",habitevent.getHabit());
    }

    /**
     * Tests the getComment() method of the HabitEvent class. Gets the comment
     * and checks to make sure it is correct.
     */
    @Test
    void TestGetComment(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals("at the gym", habitevent.getComment());
    }
    
    /**
     * Tests the getImage() method of the HabitEvent class. Gets the comment
     * and checks to make sure it is correct.
     */
    @Test
    void TestgetImage(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals("myImage", habitevent.getImage());
    }

    /**
     * Tests the getDate() method of the HabitEvent class. Gets the date as a
     * LocalDate object and checks to make sure it is correct.
     */
    @Test
    void TestGetDate(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals(LocalDate.of(2021,1,1), LocalDate.of(habitevent.getYear(),habitevent.getMonth(),habitevent.getDay()));
    }
    
    /**
     * Tests the getLat() method of the HabitEvent class.
     */
    @Test
    void TestgetLat(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals(0, habitevent.getLat());
    }
    
    /**
     * Tests the getLon() method of the HabitEvent class.
     */
    @Test
    void TestgetLon(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals(0, habitevent.getLon());
    }
    
    /**
     * Tests the getHasLocation() method of the HabitEvent class.
     */
    @Test
    void TestgetHasLocation(){
        HabitEvent habitevent = TestHabitEvent();
        assertTrue(habitevent.getHasLocation());
    }
    
    /**
     * Tests the setHabit() method of the HabitEvent Class. Changes the habit
     * for our test HabitEvent, and checks to make sure this change is reflected
     * in the object.
     */
    @Test
    void TestSetHabit(){
        HabitEvent habitevent = TestHabitEvent();
        habitevent.setHabit("Eat");
        assertEquals("Eat",habitevent.getHabit());
    }

    /**
     * Tests the setComment() method of the HabitEvent class. Changes the
     * comment for our test HabitEvent, then checks to make sure this change
     * is reflected.
     */
    @Test
    void TestSetComment(){
        HabitEvent habitevent = TestHabitEvent();
        // change the comment to a valid comment
        habitevent.setComment("hello bro.");
        assertEquals("hello bro.",habitevent.getComment());
        // add comment longer than 20 characters
        habitevent.setComment("bro today was such a tiring day!");
        assertEquals("bro today was such a",habitevent.getComment());
    }
    
    /**
     * Tests the setImage() method of the HabitEvent class.
     */
    @Test
    void TestsetImage(){
        HabitEvent habitevent = TestHabitEvent();
        habitevent.setImage("test image");
        assertEquals("test image",habitevent.getImage());
    }
    
    /**
     * Tests the getSetLocation() method of the HabitEvent class.
     */
    @Test
    void TestsetLocation(){
        HabitEvent habitevent = TestHabitEvent();
        habitevent.setLocation(new LatLng(0,0));
        assertTrue(habitevent.getHasLocation());
    }

    /**
     * Tests the setDate() method of the HabitEvent class. Changes the date of
     * our test HabitEvent object, then checks to make sure this new date is
     * reflected. Then, sets the date to null and checks to see if it is set as
     * the current date, which is the default value.
     */
    @Test
    void TestSetDate(){
        HabitEvent habitevent = TestHabitEvent();

        // set new date
        habitevent.setDate(LocalDate.of(2023,1,2));
        assertEquals(LocalDate.of(2023,1,2),LocalDate.of(habitevent.getYear(),habitevent.getMonth(),habitevent.getDay()));

        // set date to null, should be defaulted to the current date
        habitevent.setDate(null);
        assertEquals(LocalDate.now(),LocalDate.of(habitevent.getYear(),habitevent.getMonth(),habitevent.getDay()));
    }
}
