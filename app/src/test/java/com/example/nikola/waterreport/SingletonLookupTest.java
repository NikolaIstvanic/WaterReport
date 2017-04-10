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
        assertEquals(Singleton.getInstance().addToMappings(null, null), false);
    }

    @Test
    public void add_null_username() throws Exception {
        assertEquals(Singleton.getInstance().addToMappings(null, new User("", "", "User")), false);
    }

    @Test
    public void add_null_user() throws Exception {
        assertEquals(Singleton.getInstance().addToMappings("", null), false);
    }

    @Test
    public void add() throws Exception {
        assertEquals(Singleton.getInstance().addToMappings("", new User("", "", "User")), true);
    }

    @Test
    public void adding() throws Exception {
        Singleton.getInstance().addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Vishvak", new User("Vishvak", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Abhijeet", new User("Abhijeet", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Samuel", new User("Samuel", "Nikola", "User"));

        assertEquals(Singleton.getInstance().mappings.size(), 5);
        assertEquals(Singleton.getInstance().mappings.get("Nikola").getmUserName(), "Samuel");
        assertEquals(Singleton.getInstance().mappings.get("Prithviraj").getmUserName(), "Prithviraj");
    }

    @Test
    public void removing() throws Exception {
        Singleton.getInstance().addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Nikola", new User("Nikola", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Nikola", new User("Samuel", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Vishvak", new User("Vishvak", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Abhijeet", new User("Abhijeet", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Prithviraj", new User("Prithviraj", "Nikola", "User"));
        Singleton.getInstance().addToMappings("Samuel", new User("Samuel", "Nikola", "User"));

        Singleton.getInstance().mappings.remove("Jayden");
        assertEquals(Singleton.getInstance().mappings.size(), 5);

        Singleton.getInstance().mappings.remove("Nikola");
        assertEquals(Singleton.getInstance().mappings.size(), 4);
    }
}
