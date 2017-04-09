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
    Singleton tester = Singleton.getInstance();
    @Test
    public void lookup_null() throws Exception {
        assertEquals(tester.lookup(null, null), false);
    }

    @Test
    public void lookup_null_username() throws Exception {
        assertEquals(tester.lookup(null, ""), false);
    }

    @Test
    public void lookup_null_user() throws Exception {
        assertEquals(tester.lookup("", null), false);
    }

    @Test
    public void lookup() throws Exception {
        assertEquals(tester.lookup("",""), false);
    }

    @Test
    public void moreLookup() throws Exception {
        tester.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        tester.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        tester.addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        tester.addToMappings("Samuel", new User("Samuel", "Nikola", "User"));

        tester.addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));

        assertEquals(tester.mappings.get("Prithviraj").getmUserName(), "Prithviraj");
        assertEquals(tester.lookup("Nikola", ""), false);
        assertEquals(tester.lookup("Nikola", "Nikola"), true);
        assertEquals(tester.lookup("Samuel", "Nikola"), true);

    }

    @Test
    public void removingLookup() throws Exception {
        tester.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        tester.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        tester.addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        tester.addToMappings("Vishvak", new User("Vishvak", "Nikola", "User"));
        tester.addToMappings("Abhijeet", new User("Abhijeet", "Nikola", "User"));
        tester.addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));
        tester.addToMappings("Samuel", new User("Samuel", "Nikola", "User"));


        tester.mappings.remove("Jayden");

        tester.mappings.remove("Nikola");

        assertEquals(tester.lookup("Nikola", ""), false);
        assertEquals(tester.lookup("Nikola", "Nikola"), false);
        assertEquals(tester.lookup("Samuel", "Nikola"), true);
    }
}
