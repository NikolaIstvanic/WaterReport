package com.example.nikola.waterreport;

import com.example.nikola.waterreport.model.User;
import com.example.nikola.waterreport.model.Singleton;
import com.google.firebase.database.DataSnapshot;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class SingletonLookupTest {

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
    public void add_null() throws Exception {
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
        assertEquals(tester.addToMappings(null, null), false);
    }

    @Test
    public void add_null_username() throws Exception {
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
        assertEquals(tester.addToMappings(null, new User("", "", "User")), false);
    }

    @Test
    public void add_null_user() throws Exception {
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
        assertEquals(tester.addToMappings("", null), false);
    }

    @Test
    public void add() throws Exception {
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
        assertEquals(tester.addToMappings("", new User("", "", "User")), true);
    }

    @Test
    public void adding() throws Exception {
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

        assertEquals(tester.mappings.size(), 5);
        assertEquals(tester.mappings.get("Nikola").getmUserName(), "Samuel");
        assertEquals(tester.mappings.get("Prithviraj").getmUserName(), "Prithviraj");
    }

    @Test
    public void removing() throws Exception {
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
        assertEquals(tester.mappings.size(), 5);

        tester.mappings.remove("Nikola");
        assertEquals(tester.mappings.size(), 4);
    }
}
