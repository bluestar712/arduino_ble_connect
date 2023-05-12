package com.example.ardu_ble;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

// this is no need part. if you test your functions, then please test it in this activity.

public class SecActivity extends AppCompatActivity {

    private String st_val1, st_val2, st_val3, st_val4, st_val5;
    private SharedPreferences s_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sec");

        s_pref = getSharedPreferences("ardu", MODE_PRIVATE);

        st_val1 = s_pref.getString("val1", "");
        st_val2 = s_pref.getString("val2", "");
        st_val3 = s_pref.getString("val3", "");
        st_val4 = s_pref.getString("val4", "");
        st_val5 = s_pref.getString("val5", "");

        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);

        try {
            graph.removeAllSeries();
            LineGraphSeries<DataPoint> series = new LineGraphSeries <> (new DataPoint[] {

                    new DataPoint(1, Double.parseDouble(st_val1)),
                    new DataPoint(2, Double.parseDouble(st_val2)),
                    new DataPoint(3, Double.parseDouble(st_val3)),
                    new DataPoint(4, Double.parseDouble(st_val4)),
                    new DataPoint(5, Double.parseDouble(st_val5)),
            });
            graph.addSeries(series);
        } catch (IllegalArgumentException e) {
            Toast.makeText(SecActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

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
}