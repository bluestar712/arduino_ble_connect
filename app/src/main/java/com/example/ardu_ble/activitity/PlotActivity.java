package com.example.ardu_ble.activitity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ardu_ble.ConnectedThread;
import com.example.ardu_ble.R;
import com.example.ardu_ble.ReadData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

// curve page part

public class PlotActivity extends AppCompatActivity implements ReadData {


    private  LineChart lineChart;
    ConnectedThread connectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        init_view();
    }

    // sharing all values

    private void init_view(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Plot");

        lineChart = (LineChart) findViewById(R.id.chart);

        connectedThread = ConnectedThread.getInstance();
        connectedThread.setReadData(this);
        connectedThread.write("B");
    }

    // draw graph part

    private void drawGraph(String[] array){

        ArrayList<Entry> entries = new ArrayList<>();

        int arraylength = array.length;
        float max_float = Float.parseFloat(array[arraylength-3]);
        float step_float = Float.parseFloat(array[arraylength-2]);

        int max = Math.round(max_float);
        int step = Math.round(step_float);

        for(int i = 0; i < array.length -3; i ++){

            entries.add(new Entry(step * i , Float.parseFloat(array[i])));
        }


        LineDataSet dataset = new LineDataSet(entries, "");

        LineData data = new LineData(dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        lineChart.getDescription().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setDrawLabels(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setDrawGridLines(false);



        lineChart.setTouchEnabled(false);

        lineChart.getAxisLeft().setAxisMinimum(0);
        lineChart.getAxisRight().setAxisMinimum(0);

        int count = Math.round(max/step);

        if(max % step != 0){
            count = count + 1;
        }

        xAxis.setLabelCount(count + 1, true);
        xAxis.mAxisMinimum = 0;
        xAxis.mAxisMaximum = max;

        lineChart.setData(data);
        lineChart.animateY(5000);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // get curve values from arduino.

    @Override
    public void readtext(String txt) {
        String[] splits = txt.split("#");

        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawGraph(splits);
            }
        }, 500);

    }
}