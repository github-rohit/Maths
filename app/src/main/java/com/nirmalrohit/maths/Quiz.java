package com.nirmalrohit.maths;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.security.AccessController.getContext;

public class Quiz extends AppCompatActivity {

    private TextView textViewTimer;
    private TextView textView_questionSymbol;
    private TextView textView_firstNum;
    private TextView textView_secondNum;
    private TextView textView_score;
    private ProgressBar progressBar;

    RelativeLayout layout;
    private GridLayout answerLayout;

    private ArrayList<Integer> question = new ArrayList<Integer>();
    private int bgColor;
    private GenerateQA generateQA;
    private Boolean isRandom = false;
    private Random random;

    private HashMap<String, Integer> styleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        int max = getIntent().getExtras().getInt("max");
        int type = getIntent().getExtras().getInt("type");

        if (type == 5) {
            random = new Random();
            isRandom = true;
            type = getQuestionType();
        } else {
            isRandom = false;
        }

        generateQA = new GenerateQA(max, type);

        layout = findViewById(R.id.calculus);
        answerLayout = findViewById(R.id.gridLayoutAnswers);

        progressBar = findViewById(R.id.progressBar);
        textViewTimer = findViewById(R.id.text_timer);
        textView_questionSymbol = findViewById(R.id.text_symbol);
        textView_firstNum = findViewById(R.id.text_firstNum);
        textView_secondNum = findViewById(R.id.text_secondNum);
        textView_score = findViewById(R.id.text_score);

        setActivityStyle();
        setQuestionAnswerView();
        quizTimer();
    }

    private void setQuestionAnswerView() {
        generateQA.setQuestionAnswerView(textView_firstNum, textView_secondNum, answerLayout);
    }

    private void quizTimer ()  {
        progressBar.setVisibility(View.VISIBLE);
        textViewTimer.setVisibility(View.VISIBLE);
        new CountDownTimer(5100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int remain = (int) (millisUntilFinished / 1000);

                textViewTimer.setText(String.valueOf(remain) + "s");
                progressBar.setProgress(remain);

            }

            @Override
            public void onFinish() {
                textViewTimer.setText("0s");
                progressBar.setProgress(0);

                new AlertDialog.Builder(Quiz.this)
                        .setTitle("Game Over")
                        .setMessage("Your final score is" + generateQA.getTotalCorrectAnswers() + " answered correctly out of " + generateQA.getTotalQuestions())
                        .setNegativeButton("CLOSE", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setPositiveButton("RE START", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restart();
                            }
                        }).show();
            }
        }.start();
    }

    private void setActivityStyle () {
        styleMap = generateQA.getQAStyle();
        bgColor = ContextCompat.getColor(this, styleMap.get("bgColor"));

        textView_questionSymbol.setText(styleMap.get("symbol"));
        layout.setBackgroundColor(bgColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(bgColor));
        setTitle(styleMap.get("title"));
    }

    private int getQuestionType () {
        return random.nextInt(4) + 1;
    }

    private void restart () {
        generateQA.setTotalCorrectAnswers(0);
        generateQA.setIsAnswered(false);
        generateQA.setTotalQuestions(0);

        if (isRandom) {
            generateQA.setQuestionType(getQuestionType());
            setActivityStyle();
        }

        generateQA.setProgressTextView(textView_score);

        setQuestionAnswerView();
        quizTimer();
    }

    public void checkAnswer(View view) {
        generateQA.setAnsweredView(view, false);
        generateQA.setProgressTextView(textView_score);

        if (isRandom) {
            generateQA.setQuestionType(getQuestionType());
            setActivityStyle();
        }

        setQuestionAnswerView();
    }
}
