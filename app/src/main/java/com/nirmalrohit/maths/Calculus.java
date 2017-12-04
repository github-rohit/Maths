package com.nirmalrohit.maths;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Calculus extends AppCompatActivity {

    private TextView textView_questionSymbol;
    private TextView textView_firstNum;
    private TextView textView_secondNum;
    private TextView textView_score;

    private Button btnNext;

    private GridLayout answerLayout;

    private ArrayList<Integer> question = new ArrayList<Integer>();
    private int bgColor;
    private GenerateQA generateQA;

    private HashMap<String, Integer> styleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        final int type = getIntent().getExtras().getInt("type");
        final int max = getIntent().getExtras().getInt("max");

        RelativeLayout layout = findViewById(R.id.calculus);
        findViewById(R.id.button_next).setVisibility(View.VISIBLE);

        generateQA = new GenerateQA(max, type);

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

        setQuestionView();
        setAnswerView();

    }

    private void setQuestionView() {
        generateQA.setQuestionView(textView_firstNum, textView_secondNum);
    }

    private void setAnswerView() {
        generateQA.setAnswersView(answerLayout);
    }

    public void checkAnswer (View view) {
        if (generateQA.getIsAnsweredCorrectly() == true) {
            return;
        }

        generateQA.setAnsweredView(view, true);
        generateQA.setProgressTextView(textView_score);
    }

    public void nextQuestion (View view) {

        if (generateQA.getIsAnswered() == false) {
            generateQA.setProgressTextView(textView_score);
        }

        setQuestionView();
        setAnswerView();
    }
}
