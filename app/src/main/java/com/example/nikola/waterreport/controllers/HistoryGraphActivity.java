package com.example.nikola.waterreport.controllers;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikola.waterreport.R;
import com.example.nikola.waterreport.model.Singleton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HistoryGraphActivity extends AppCompatActivity {
    private EditText locationField;
    private EditText yearField;
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historygraphlookup);
        yearField = (EditText) findViewById(R.id.year);
        graph = (GraphView) findViewById(R.id.graph);
        locationField = (EditText) findViewById(R.id.location);
        locationField.requestFocus();
        Button graph = (Button) findViewById(R.id.graph_button);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphCreate();
            }
        });
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Creates graph of virus/contaminant PPM vs time
     */
    public void graphCreate() {
        String location = locationField.getText().toString();
        String year = yearField.getText().toString();
        if (!year.equals("") && !location.equals("")) {
            Map<Integer, Double> VPPMGraphPoints = Singleton.VPPMValues(location, year);
            Map<Integer, Double> CPPMGraphPoints = Singleton.CPPMValues(location, year);
            List<DataPoint> CPPMValues = new ArrayList<>();
            List<DataPoint> VPPMValues = new ArrayList<>();
            Integer[] keySet = new Integer[CPPMGraphPoints.keySet().size()];
            int i = 0;
            for (Integer key : CPPMGraphPoints.keySet()) {
                keySet[i] = key;
                i++;
            }
            Arrays.sort(keySet);
            for (Integer key : keySet) {
                Double val1 = CPPMGraphPoints.get(key);
                Double val2 = VPPMGraphPoints.get(key);
                DataPoint point1 = new DataPoint(key, val1);
                DataPoint point2 = new DataPoint(key, val2);
                CPPMValues.add(point1);
                VPPMValues.add(point2);
            }
            DataPoint[] CPPMArray = new DataPoint[CPPMValues.size()];
            DataPoint[] VPPMArray = new DataPoint[VPPMValues.size()];
            for (int j = 0; j < CPPMArray.length; i++) {
                CPPMArray[j] = CPPMValues.get(j);
                VPPMArray[j] = VPPMValues.get(j);
            }
            LineGraphSeries<DataPoint> CPPMseries = new LineGraphSeries<>(CPPMArray);
            CPPMseries.setTitle("PPM over " + year);
            CPPMseries.setDrawDataPoints(true);
            CPPMseries.setColor(Color.GREEN);
            graph.addSeries(CPPMseries);
            LineGraphSeries<DataPoint> VPPMseries = new LineGraphSeries<>(VPPMArray);
            VPPMseries.setTitle("PPM over " + year);
            VPPMseries.setDrawDataPoints(true);
            VPPMseries.setColor(Color.RED);
            graph.addSeries(VPPMseries);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Empty field(s)";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
