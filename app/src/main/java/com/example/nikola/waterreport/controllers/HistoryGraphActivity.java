package com.example.nikola.waterreport.controllers;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
        TextView virus_text = (TextView) findViewById(R.id.virus_text);
        virus_text.setTextColor(Color.RED);
        TextView contaminant_text = (TextView) findViewById(R.id.contaminant_text);
        contaminant_text.setTextColor(Color.CYAN);
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
            Map<Integer, Double> VPPMGraphPoints = Singleton.getInstance().VPPMValues(location, year);
            Map<Integer, Double> CPPMGraphPoints = Singleton.getInstance().CPPMValues(location, year);
            List<DataPoint> VPPMValues = new ArrayList<>();
            List<DataPoint> CPPMValues = new ArrayList<>();
            Integer[] keySet = new Integer[CPPMGraphPoints.keySet().size()];
            int i = 0;
            for (Integer key : CPPMGraphPoints.keySet()) {
                keySet[i++] = key;
            }
            Arrays.sort(keySet);
            for (Integer key : keySet) {
                Double vppm = VPPMGraphPoints.get(key);
                Double cppm = CPPMGraphPoints.get(key);
                DataPoint point1 = new DataPoint(key, vppm);
                DataPoint point2 = new DataPoint(key, cppm);
                VPPMValues.add(point1);
                CPPMValues.add(point2);
            }
            DataPoint[] VPPMArray = new DataPoint[VPPMValues.size()];
            DataPoint[] CPPMArray = new DataPoint[CPPMValues.size()];
            for (int j = 0; j < CPPMArray.length; j++) {
                VPPMArray[j] = VPPMValues.get(j);
                CPPMArray[j] = CPPMValues.get(j);
            }
            LineGraphSeries<DataPoint> VPPMSeries = new LineGraphSeries<>(VPPMArray);
            VPPMSeries.setTitle("PPM over " + year);
            VPPMSeries.setDrawDataPoints(true);
            VPPMSeries.setColor(Color.RED);
            graph.addSeries(VPPMSeries);
            LineGraphSeries<DataPoint> CPPMSeries = new LineGraphSeries<>(CPPMArray);
            CPPMSeries.setTitle("PPM over " + year);
            CPPMSeries.setDrawDataPoints(true);
            CPPMSeries.setColor(Color.CYAN);
            graph.addSeries(CPPMSeries);
            graph.setTitle("Virus and Contaminant PPM by month in " + location);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Empty field(s)";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
