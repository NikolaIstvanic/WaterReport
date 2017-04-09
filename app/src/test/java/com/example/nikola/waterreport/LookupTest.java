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
        assertEquals(Singleton.lookup(null, null), false);
    }

    @Test
    public void lookup_null_username() throws Exception {
        assertEquals(Singleton.lookup(null, ""), false);
    }

    @Test
    public void lookup_null_user() throws Exception {
        assertEquals(Singleton.lookup("", null), false);
    }

    @Test
    public void lookup() throws Exception {
        assertEquals(Singleton.lookup("",""), false);
    }

    @Test
    public void moreLookup() throws Exception {
        Singleton.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        Singleton.addToMappings("Samuel", new User("Samuel", "Nikola", "User"));

        Singleton.addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));

        assertEquals(Singleton.mappings.get("Prithviraj").getmUserName(), "Prithviraj");
        assertEquals(Singleton.lookup("Nikola", ""), false);
        assertEquals(Singleton.lookup("Nikola", "Nikola"), true);
        assertEquals(Singleton.lookup("Samuel", "Nikola"), true);

    }

    @Test
    public void removingLookup() throws Exception {
        Singleton.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        Singleton.addToMappings("Vishvak", new User("Vishvak", "Nikola", "User"));
        Singleton.addToMappings("Abhijeet", new User("Abhijeet", "Nikola", "User"));
        Singleton.addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));
        Singleton.addToMappings("Samuel", new User("Samuel", "Nikola", "User"));


        Singleton.mappings.remove("Jayden");

        Singleton.mappings.remove("Nikola");

        assertEquals(Singleton.lookup("Nikola", ""), false);
        assertEquals(Singleton.lookup("Nikola", "Nikola"), false);
        assertEquals(Singleton.lookup("Samuel", "Nikola"), true);
    }
}
