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
    @Test
    public void add_null() throws Exception {
        assertEquals(Singleton.addToMappings(null, null), false);
    }

    @Test
    public void add_null_username() throws Exception {
        assertEquals(Singleton.addToMappings(null, new User("", "", "User")), false);
    }

    @Test
    public void add_null_user() throws Exception {
        assertEquals(Singleton.addToMappings("", null), false);
    }

    @Test
    public void add() throws Exception {
        assertEquals(Singleton.addToMappings("", new User("", "", "User")), true);
    }

    @Test
    public void adding() throws Exception {
        Singleton.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        Singleton.addToMappings("Vishvak", new User("Vishvak", "Nikola", "User"));
        Singleton.addToMappings("Abhijeet", new User("Abhijeet", "Nikola", "User"));
        Singleton.addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));
        Singleton.addToMappings("Samuel", new User("Samuel", "Nikola", "User"));

        assertEquals(Singleton.mappings.size(), 5);
        assertEquals(Singleton.mappings.get("Nikola").getmUserName(), "Samuel");
        assertEquals(Singleton.mappings.get("Prithviraj").getmUserName(), "Prithviraj");
    }

    @Test
    public void removing() throws Exception {
        Singleton.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        Singleton.addToMappings("Vishvak", new User("Vishvak", "Nikola", "User"));
        Singleton.addToMappings("Abhijeet", new User("Abhijeet", "Nikola", "User"));
        Singleton.addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));
        Singleton.addToMappings("Samuel", new User("Samuel", "Nikola", "User"));

        Singleton.mappings.remove("Jayden");
        assertEquals(Singleton.mappings.size(), 5);

        Singleton.mappings.remove("Nikola");
        assertEquals(Singleton.mappings.size(), 4);
    }
}
