package com.nirmalrohit.maths;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class TimesTables extends AppCompatActivity {

    private final int MAX_SEEK = 20;
    private final int MIN_SEEK = 1;
    private final int START_SEEK = 10;

    private SeekBar seekBar;
    private ListView listView;
    private int seekBarValue;
    private ArrayList<String> items = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_tables);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        listView = (ListView) findViewById(R.id.list_times_tables);

        seekBar.setMax(MAX_SEEK);
        seekBar.setProgress(START_SEEK);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("progress: ", Integer.toString(progress));

                if (progress == 0) {
                    seekBar.setProgress(MIN_SEEK);
                    progress = MIN_SEEK;
                }

                createTable(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        createTable(START_SEEK);

    }

    private void createTable(int progress) {
        items.clear();

        for (int i = 1; i <= 10; i++ ) {
            String step = progress + " X " + i + " = " + progress*i;
            items.add(step);
        }
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(TimesTables.this, R.layout.table_row, items);
        listView.setAdapter(itemsAdapter);
    }

}
