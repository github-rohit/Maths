package com.nirmalrohit.maths;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    public void selectModule(View view) {
        int index = Integer.parseInt( view.getTag().toString() );
        Intent intent;
        Log.i("index", Integer.toString(index));
        if (index == 0) {
            intent = new Intent(MainActivity.this, TimesTables.class);
        } else if (index == 5) {
            intent = new Intent(MainActivity.this, Quizzes.class);
        } else {
            intent = new Intent(MainActivity.this, Level.class);
        }

        intent.putExtra("type", index);

        startActivity(intent);
    }
}

