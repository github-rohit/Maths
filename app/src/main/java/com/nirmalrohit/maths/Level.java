package com.nirmalrohit.maths;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;

public class Level extends AppCompatActivity {
    private GenerateQA generateQA;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        type = getIntent().getExtras().getInt("type");

        RelativeLayout layout = findViewById(R.id.activity_level);

        GenerateQA.TYPE = type;
        generateQA = new GenerateQA();

        HashMap<String, Integer> styleMap = generateQA.getQAStyle();

        styleMap = generateQA.getQAStyle();
        int bgColor = ContextCompat.getColor(this, styleMap.get("bgColor"));

        layout.setBackgroundColor(bgColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(bgColor));
    }

    public void startLearning (View view) {
        int max = Integer.parseInt( view.getTag().toString() );
        Boolean quiz = getIntent().getExtras().getBoolean("quiz");
        Intent intent;

        if (quiz == true) {
            intent = new Intent(Level.this, Quiz.class);
        } else {
            intent = new Intent(Level.this, Calculus.class);
        }

        intent.putExtra("type", type);
        intent.putExtra("max", max);

        startActivity(intent);

    }
}
