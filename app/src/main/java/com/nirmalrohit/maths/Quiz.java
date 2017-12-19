package com.nirmalrohit.maths;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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

    private final int TIMER_SEC = 30100;

    private TextView textViewTimer;
    private TextView textView_questionSymbol;
    private TextView textView_firstNum;
    private TextView textView_secondNum;
    private TextView textView_score;
    private ProgressBar progressBar;

    private RelativeLayout layout;
    private GridLayout answerLayout;

    private CountDownTimer countDownTimer;

    private ArrayList<HashMap> questions = new ArrayList<>();
    private int max;
    private int type;
    private int bgColor;
    private int bgImage;
    private int millisUntilRemaining;
    private GenerateQA generateQA;
    private Boolean isRandom = false;
    private Boolean isPause = false;
    private Random random;
    private StyleUtils styleUtils;

    private HashMap<String, Integer> styleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);
        styleUtils = new StyleUtils(Quiz.this);

        max = getIntent().getExtras().getInt("max");
        type = getIntent().getExtras().getInt("type");
        int styleType;

        if (type == 5) {
            random = new Random();
            isRandom = true;
            styleType = getQuestionType();
        } else {
            isRandom = false;
            styleType = type;
        }

        generateQA = new GenerateQA(max, styleType);

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
        quizTimer(TIMER_SEC);
    }

    private void setQuestionAnswerView() {
        HashMap<String, Object> question = generateQA.setQuestionAnswerView(textView_firstNum, textView_secondNum, answerLayout);
        questions.add(question);
    }

    private void quizTimer (int milliSec)  {
        progressBar.setVisibility(View.VISIBLE);
        textViewTimer.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(milliSec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisUntilRemaining = (int) millisUntilFinished;
                int inSec = (int) (millisUntilFinished / 1000);

                textViewTimer.setText(String.valueOf(inSec) + "s");
                progressBar.setProgress(inSec);

            }

            @Override
            public void onFinish() {
                textViewTimer.setText("0s");
                progressBar.setProgress(0);

                Intent intent = new Intent(Quiz.this, QuizResult.class);
                intent.putExtra("max", max);
                intent.putExtra("type", type);
                intent.putExtra("questions", questions);
                intent.putExtra("correctAnswerCount", generateQA.getTotalCorrectAnswers());
                intent.putExtra("wrongAnswerCount", generateQA.getTotalWrongAnswerCount());
                intent.putExtra("skipAnswerCount", 0);

                finish();

                startActivity(intent);

            }
        }.start();
    }

    private void setActivityStyle () {
        styleMap = generateQA.getQAStyle();
        bgColor = ContextCompat.getColor(this, styleMap.get("bgColor"));
        bgImage = styleMap.get("bgImage");

        textView_questionSymbol.setText(styleMap.get("symbol"));
        layout.setBackgroundResource(bgImage);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(bgColor));
        changeTypeFace(styleMap.get("title"));
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
        quizTimer(TIMER_SEC);
    }

    private void setUserAnswer (int answerIndex) {
        int index = questions.size() - 1;

        HashMap<String, Object> item = questions.get(index);

        item.put("your_answer", answerIndex);

        questions.set(index, item);
    }

    private void changeTypeFace(int title) {

        TextView textView = styleUtils.getActionBarCustomTitleView(title, R.color.colorWhite, "casual");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        getSupportActionBar().setCustomView(textView);
    }

    public void checkAnswer(View view) {

        int index = Integer.parseInt( view.getTag().toString() );

        setUserAnswer(index);

        generateQA.setAnsweredView(view, false);
        generateQA.setProgressTextView(textView_score);

        if (isRandom) {
            generateQA.setQuestionType(getQuestionType());
            setActivityStyle();
        }

        setQuestionAnswerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isPause) {
            quizTimer(millisUntilRemaining);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        quizTimer(millisUntilRemaining);
    }

    @Override
    public void onPause () {
        super.onPause();
        isPause = true;
        countDownTimer.cancel();
    }

    @Override
    public void onStop () {
        super.onStop();
        isPause = false;
        countDownTimer.cancel();
    }

    @Override
    public void onDestroy () {
        super.onDestroy();

        countDownTimer.cancel();
    }
}
