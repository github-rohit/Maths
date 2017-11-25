package com.nirmalrohit.maths;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ImageButton btnTimesTables = (ImageButton) findViewById(R.id.btn_times_tables);

        btnTimesTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimesTables.class);
                startActivity(intent);
            }
        });
    }

    public void calculus(View view) {
        int index = Integer.parseInt( view.getTag().toString() );

        Intent intent = new Intent(MainActivity.this, Calculus.class);
        intent.putExtra("type", index);

        startActivity(intent);
    }
}
