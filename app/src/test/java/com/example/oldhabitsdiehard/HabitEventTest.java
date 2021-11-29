package com.example.oldhabitsdiehard;

        import static org.junit.Assert.assertTrue;
        import static org.junit.jupiter.api.Assertions.assertEquals;

        import android.location.Location;
        import android.location.LocationManager;

        import com.google.android.gms.maps.model.LatLng;

        import org.junit.jupiter.api.Test;

        import java.time.LocalDate;

/**
 * Testing for HabitEvent
 */
public class HabitEventTest {
    private HabitEvent TestHabitEvent(){
        String habitTitle = "Exercise";
        LocalDate myDate = LocalDate.of(2021,1,1);
        HabitEvent habitevent = new HabitEvent(habitTitle, "at the gym","myImage", myDate,new LatLng(0, 0));
        return habitevent;
    }
    @Test
    void TestingConstructor(){
        LocalDate myDate = LocalDate.of(2021,1,1);
        HabitEvent habitevent = TestHabitEvent();
        Location location = new Location(LocationManager.PASSIVE_PROVIDER);
        assertEquals("Exercise",habitevent.getHabit());
        assertEquals(myDate,LocalDate.of(habitevent.getYear(),habitevent.getMonth(),habitevent.getDay()));
        assertEquals("at the gym",habitevent.getComment());
        assertEquals("myImage",habitevent.getImage());
        assertTrue(habitevent.getHasLocation());
    }

    @Test
    void TestgetHabit(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals("Exercise",habitevent.getHabit());
    }

    @Test
    void TestgetComment(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals("at the gym", habitevent.getComment());
    }

    @Test
    void TestgetImage(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals("myImage", habitevent.getImage());
    }


    @Test
    void TestgetDate(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals(LocalDate.of(2021,1,1), LocalDate.of(habitevent.getYear(),habitevent.getMonth(),habitevent.getDay()));
    }

    @Test
    void TestgetLat(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals(0, habitevent.getLat());
    }

    @Test
    void TestgetLon(){
        HabitEvent habitevent = TestHabitEvent();
        assertEquals(0, habitevent.getLon());
    }

    @Test
    void TestgetHasLocation(){
        HabitEvent habitevent = TestHabitEvent();
        assertTrue(habitevent.getHasLocation());
    }

    @Test
    void TestsetHabit(){
        HabitEvent habitevent = TestHabitEvent();
        habitevent.setHabit("Eat");
        assertEquals("Eat",habitevent.getHabit());
    }

    @Test
    void TestsetComment(){
        HabitEvent habitevent = TestHabitEvent();
        habitevent.setComment("hello bro.");
        assertEquals("hello bro.",habitevent.getComment());
        //Above 20 characters
        habitevent.setComment("bro today was such a tiring day!");
        assertEquals("bro today was such a",habitevent.getComment());
    }

    @Test
    void TestsetImage(){
        HabitEvent habitevent = TestHabitEvent();
        habitevent.setImage("test image");
        assertEquals("test image",habitevent.getImage());
    }

    @Test
    void TestsetLocation(){
        HabitEvent habitevent = TestHabitEvent();
        habitevent.setLocation(new LatLng(0,0));
        assertTrue(habitevent.getHasLocation());
    }


    @Test
    void TestsetDate(){
        HabitEvent habitevent = TestHabitEvent();
        habitevent.setDate(LocalDate.of(2023,1,2));
        assertEquals(LocalDate.of(2023,1,2),LocalDate.of(habitevent.getYear(),habitevent.getMonth(),habitevent.getDay()));
        //Date is left empty
        habitevent.setDate(null);
        assertEquals(LocalDate.now(),LocalDate.of(habitevent.getYear(),habitevent.getMonth(),habitevent.getDay()));
    }
}