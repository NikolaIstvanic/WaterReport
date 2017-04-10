package com.example.nikola.waterreport;

import com.example.nikola.waterreport.model.QualityReport;
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
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
public class GraphTests {

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
    public void nullPPM() {
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

        HashMap<Integer, Double> valid = new HashMap<>();
        assertEquals(tester.VPPMValues(null, null), valid);
        assertEquals(tester.CPPMValues(null, null), valid);
    }

    @Test
    public void validateVPPM() throws Exception {
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

        QualityReport jeet = new QualityReport("Jeet", "Date: 04/08/2017 18:23", 1, "North Korea", "Safe", 5, 4, 127.11, 127.11);
        QualityReport jeet1 = new QualityReport("Jeet", "Date: 04/08/2017 18:23", 1, "North Korea", "Safe", 9, 3, 127.11, 127.11);
        QualityReport jeet2 = new QualityReport("Jeet", "Date: 05/08/2017 18:23", 1, "North Korea", "Safe", 9, 3, 127.11, 127.11);
        QualityReport fakeJeet = new QualityReport("Jeet", "Date: 05/08/2017 18:23", 1, "India", "Safe", 9, 3, 111, 111);

        HashMap<Integer, Double> valid = new HashMap<>();
        //Resting 0 avg
        assertEquals(valid, tester.VPPMValues("", ""));
        tester.qualityreports.clear();

        //Testing 1 element
        valid.put(4, 5.0);
        tester.qualityreports.add(jeet);
        assertEquals(valid, tester.VPPMValues("North Korea", "2017"));

        //Testing multiple averages
        valid.put(4, 7.0);
        tester.qualityreports.add(jeet1);
        assertEquals(valid, tester.VPPMValues("North Korea", "2017"));

        //Testing multiple months
        valid.put(5, 9.0);
        tester.qualityreports.add(jeet2);
        assertEquals(valid, tester.VPPMValues("North Korea", "2017"));

        //Testing more locations
        valid.clear();
        valid.put(5, 9.0);
        tester.qualityreports.add(fakeJeet);
        assertEquals(valid, tester.VPPMValues("India", "2017"));

    }

    @Test
    public void validateCPPM() throws Exception {

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

        QualityReport jeet = new QualityReport("Jeet", "Date: 04/08/2017 18:23", 1, "North Korea", "Safe", 5, 4, 127.11, 127.11);
        QualityReport jeet1 = new QualityReport("Jeet", "Date: 04/08/2017 18:23", 1, "North Korea", "Safe", 9, 3, 127.11, 127.11);
        QualityReport jeet2 = new QualityReport("Jeet", "Date: 05/08/2017 18:23", 1, "North Korea", "Safe", 9, 3, 127.11, 127.11);
        QualityReport fakeJeet = new QualityReport("Jeet", "Date: 05/08/2017 18:23", 1, "India", "Safe", 9, 3, 111, 111);

        HashMap<Integer, Double> valid = new HashMap<>();
        //Resting 0 avg
        assertEquals(valid, tester.VPPMValues("", ""));

        //Testing 1 element
        valid.put(4, 4.0);
        tester.qualityreports.add(jeet);
        assertEquals(valid, tester.CPPMValues("North Korea", "2017"));

        //Testing multiple averages
        valid.put(4, 3.5);
        tester.qualityreports.add(jeet1);
        assertEquals(valid, tester.CPPMValues("North Korea", "2017"));

        //Testing multiple months
        valid.put(5, 3.0);
        tester.qualityreports.add(jeet2);
        assertEquals(valid, tester.CPPMValues("North Korea", "2017"));

        //Testing more locations
        valid.clear();
        valid.put(5, 3.0);
        tester.qualityreports.add(fakeJeet);
        assertEquals(valid, tester.CPPMValues("India", "2017"));
    }
}
