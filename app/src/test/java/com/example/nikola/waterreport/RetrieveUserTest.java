package com.example.nikola.waterreport;

import android.util.Log;

import com.example.nikola.waterreport.model.User;
import com.example.nikola.waterreport.model.Singleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class RetrieveUserTest {

    private DatabaseReference mockedDatabaseReference;
    private static ArrayList<User> testUsers = new ArrayList<>();

    @Before
    public void before() {
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);

        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedFirebaseDatabase);
    }


    @Test
    public void checkIfAdded() {
        when(mockedDatabaseReference.child(anyString())).thenReturn(mockedDatabaseReference);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener valueEventListener = (ValueEventListener) invocation.getArguments()[0];

                DataSnapshot mockedDataSnapshot = Mockito.mock(DataSnapshot.class);
                //when(mockedDataSnapshot.getValue(User.class)).thenReturn(testOrMockedUser)

                valueEventListener.onDataChange(mockedDataSnapshot);
                //valueEventListener.onCancelled(...);

                return null;
            }
        }).when(mockedDatabaseReference).addListenerForSingleValueEvent(any(ValueEventListener.class));

        Singleton tester = Singleton.getInstance();

        //System.out.print("aaaaa");
        tester.updateLocal();
        final User nikola = new User("Nikola", "Nikola", "User");
        final User samuel = new User("Samuel", "Samuel", "User");
        final User prithviraj = new User("Prithviraj", "Prithviraj", "User");
        final User abhijeet = new User("Abhijeet", "Abhijeet", "User");
        final User vishvak = new User("Vishvak", "Vishvak", "User");
        User[] uArray = {nikola, samuel, prithviraj, abhijeet, vishvak};
        for (int i = 0; i < uArray.length; ++i) {
            tester.addUser(uArray[i]);
            tester.updateLocal();
            assertEquals(i + 1, tester.mappings.keySet().size());
            assertEquals(true, tester.mappings.get(uArray[i].getmUserName()) != null);
        }

    }
}
