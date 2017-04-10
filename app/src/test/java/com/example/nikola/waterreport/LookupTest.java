package com.example.nikola.waterreport;

import android.util.Log;

import com.example.nikola.waterreport.model.QualityReport;
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
public class LookupTest {

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
    public void lookup_null() throws Exception {
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
        assertEquals(tester.lookup(null, null), false);
    }

    @Test
    public void lookup_null_username() throws Exception {
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
        assertEquals(tester.lookup(null, ""), false);
    }

    @Test
    public void lookup_null_user() throws Exception {
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
        assertEquals(tester.lookup("", null), false);
    }

    @Test
    public void lookup() throws Exception {
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
        assertEquals(tester.lookup("",""), false);
    }

    @Test
    public void moreLookup() throws Exception {
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
