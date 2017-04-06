package com.example.nikola.waterreport.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.User;

import java.util.regex.Pattern;

/**
 * Created by Prithviraj Ammanabrolu on 4/5/17
 */

public class HistoryGraphActivity extends AppCompatActivity {
    /*
    // UI references.
    private EditText mLocation;
    private EditText mVirus;
    private EditText mYear;
    private User mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_historygraphlookup);
        super.onCreate(savedInstanceState);
        Button mSubmit = (Button)findViewById(R.id.reg_graph_Submit);

        mLocation = (EditText) findViewById(R.id.reg_loc);
        mVirus = (EditText) findViewById(R.id.reg_Home);
        mYear = (EditText) findViewById(R.id.year_home);

        //Loopkup into db and then make graph here

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

    }

    private void submit_profile() {
        // get data from the UI elements and add it to the User Model
        //validating user input
        // find the user using Login manager
        // Check for a valid ID.
        /*
        if (TextUtils.isEmpty(mEmailAddress.getText().toString())) {
            mEmailAddress.setError(getString(R.string.error_field_required));
            return;
        } else if (!isIDValid(mEmailAddress.getText().toString())) {
            mEmailAddress.setError(getString(R.string.error_invalid_email));
            return;
        }
        // errror checks made. need to change the model
        mCurrentUser.setEmail(mEmailAddress.getText().toString());
        mCurrentUser.setHomeAddress(mHomeAddress.getText().toString());
        mCurrentUser.setTitle((String) mTitle.getSelectedItem());
        Context context = getApplicationContext();
        CharSequence text = "Changes Submitted !!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        finish();
        toast.show();
    }

    private boolean isIDValid(String id) {
        return Pattern.matches("[A-Za-z0-9\\._%+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,4}", id);
    }
    */
}

