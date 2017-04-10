package com.example.nikola.waterreport;

import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests adding to Singleton "database"
 *
 * @author prithviraj
 */
public class LookupTest {
    @Test
    public void lookup_null() throws Exception {
        assertEquals(Singleton.getInstance().lookup(null, null), false);
    }

    @Test
    public void lookup_null_username() throws Exception {
        assertEquals(Singleton.getInstance().lookup(null, ""), false);
    }

    @Test
    public void lookup_null_user() throws Exception {
        assertEquals(Singleton.getInstance().lookup("", null), false);
    }

    @Test
    public void lookup() throws Exception {
        assertEquals(Singleton.getInstance().lookup("",""), false);
    }

    @Test
    public void moreLookup() throws Exception {
        Singleton.getInstance().addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Samuel", new User("Samuel", "Nikola", "User"));

        Singleton.getInstance().addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));

        assertEquals(Singleton.getInstance().mappings.get("Prithviraj").getmUserName(), "Prithviraj");
        assertEquals(Singleton.getInstance().lookup("Nikola", ""), false);
        assertEquals(Singleton.getInstance().lookup("Nikola", "Nikola"), true);
        assertEquals(Singleton.getInstance().lookup("Samuel", "Nikola"), true);

    }

    @Test
    public void removingLookup() throws Exception {
        Singleton.getInstance().addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Vishvak", new User("Vishvak", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Abhijeet", new User("Abhijeet", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Samuel", new User("Samuel", "Nikola", "User"));


        Singleton.getInstance().mappings.remove("Jayden");

        Singleton.getInstance().mappings.remove("Nikola");

        assertEquals(Singleton.getInstance().lookup("Nikola", ""), false);
        assertEquals(Singleton.getInstance().lookup("Nikola", "Nikola"), false);
        assertEquals(Singleton.getInstance().lookup("Samuel", "Nikola"), true);
    }
}
