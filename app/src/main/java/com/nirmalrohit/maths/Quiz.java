package com.nirmalrohit.maths;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Quiz extends AppCompatActivity {

    private TextView textViewTimer;
    private TextView textView_questionSymbol;
    private TextView textView_firstNum;
    private TextView textView_secondNum;
    private TextView textView_score;

    private GridLayout answerLayout;

    private ArrayList<Integer> question = new ArrayList<Integer>();
    private int bgColor;
    private GenerateQA generateQA;

    private HashMap<String, Integer> styleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);
        textViewTimer = findViewById(R.id.text_timer);

        final int TYPE = getIntent().getExtras().getInt("type");

        RelativeLayout layout = findViewById(R.id.calculus);

        GenerateQA.MAX = getIntent().getExtras().getInt("max");
        GenerateQA.TYPE = TYPE;
        generateQA = new GenerateQA();

        answerLayout = findViewById(R.id.gridLayoutAnswers);
        textView_questionSymbol = findViewById(R.id.text_symbol);
        textView_firstNum = findViewById(R.id.text_firstNum);
        textView_secondNum = findViewById(R.id.text_secondNum);
        textView_score = findViewById(R.id.text_score);


        styleMap = generateQA.getQAStyle();
        bgColor = ContextCompat.getColor(this, styleMap.get("bgColor"));

        textView_questionSymbol.setText(styleMap.get("symbol"));
        layout.setBackgroundColor(bgColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(bgColor));
        setTitle(styleMap.get("title"));

        quizTimer();
    }

    private void quizTimer ()  {
        textViewTimer.setVisibility(View.VISIBLE);
        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}
