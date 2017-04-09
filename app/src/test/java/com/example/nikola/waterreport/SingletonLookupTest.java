package com.example.nikola.waterreport;

import com.example.nikola.waterreport.model.Singleton;
import com.example.nikola.waterreport.model.User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests adding to Singleton "database"
 *
 * @author Nikola Istvanic
 */
public class SingletonLookupTest {
    Singleton tester = Singleton.getInstance();
    @Test
    public void add_null() throws Exception {
        assertEquals(tester.addToMappings(null, null), false);
    }

    @Test
    public void add_null_username() throws Exception {
        assertEquals(tester.addToMappings(null, new User("", "", "User")), false);
    }

    @Test
    public void add_null_user() throws Exception {
        assertEquals(tester.addToMappings("", null), false);
    }

    @Test
    public void add() throws Exception {
        assertEquals(tester.addToMappings("", new User("", "", "User")), true);
    }

    @Test
    public void adding() throws Exception {
        tester.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        tester.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        tester.addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        tester.addToMappings("Vishvak", new User("Vishvak", "Nikola", "User"));
        tester.addToMappings("Abhijeet", new User("Abhijeet", "Nikola", "User"));
        tester.addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));
        tester.addToMappings("Samuel", new User("Samuel", "Nikola", "User"));

        assertEquals(tester.mappings.size(), 5);
        assertEquals(tester.mappings.get("Nikola").getmUserName(), "Samuel");
        assertEquals(tester.mappings.get("Prithviraj").getmUserName(), "Prithviraj");
    }

    @Test
    public void removing() throws Exception {
        tester.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        tester.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        tester.addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        tester.addToMappings("Vishvak", new User("Vishvak", "Nikola", "User"));
        tester.addToMappings("Abhijeet", new User("Abhijeet", "Nikola", "User"));
        tester.addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));
        tester.addToMappings("Samuel", new User("Samuel", "Nikola", "User"));

        tester.mappings.remove("Jayden");
        assertEquals(tester.mappings.size(), 5);

        tester.mappings.remove("Nikola");
        assertEquals(tester.mappings.size(), 4);
    }
}
