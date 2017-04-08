package com.example.nikola.waterreport;

import android.util.Log;

import com.example.nikola.waterreport.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Nikola Istvanic
 */

public class RetrieveUserTest {
    private static FirebaseDatabase fdb = FirebaseDatabase.getInstance();

    public void clearAndAdd(final User userToAdd) {
        DatabaseReference users = fdb.getReference().child("TestUsers");
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    d.getRef().removeValue();
                }
                // This method is called once with the initial value and again whenever data at this location is updated.
                Object o = dataSnapshot.getValue();
                if (!(o instanceof ArrayList)) {
                    ArrayList<Map<String, Map<String, String>>> dbList = new ArrayList<>();
                    dbList.add(userToAdd.getMap());
                    fdb.getReference().child("TestUsers").setValue(dbList);
                } else {
                    ArrayList existingList = (ArrayList) o;
                    boolean valid = true;
                    for (Object h : existingList) {
                        if (h instanceof HashMap) {
                            if (((HashMap) h).containsKey(userToAdd.getmUserName())) {
                                valid = false;
                                break;
                            }
                        }
                    }
                    if (valid) {
                        existingList.add(userToAdd.getMap());
                        fdb.getReference().child("Users").setValue(existingList);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FAILURE", "Failed to read value.", error.toException());
            }
        });
    }

    @Test
    public void checkIfAdded() {
        final User nikola = new User("Nikola", "Nikola");
        final User samuel = new User("Samuel", "Samuel");
        final User prithviraj = new User("Prithviraj", "Prithviraj");
        final User abhijeet = new User("Abhijeet", "Abhijeet");
        final User vishvak = new User("Vishvak", "Vishvak");

    }
}
