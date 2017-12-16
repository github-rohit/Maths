package com.nirmalrohit.maths;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
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

    private MediaPlayer mediaPlayer;

    private TextView textView_questionSymbol;
    private TextView textView_firstNum;
    private TextView textView_secondNum;
    private TextView textView_score;

    private Button btnNext;

    private GridLayout answerLayout;

    private ArrayList<Integer> question = new ArrayList<Integer>();
    private int bgColor;
    private int bgImage;
    private GenerateQA generateQA;
    private StyleUtils styleUtils;

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
        styleUtils = new StyleUtils(this);

        answerLayout = findViewById(R.id.gridLayoutAnswers);
        textView_questionSymbol = findViewById(R.id.text_symbol);
        textView_firstNum = findViewById(R.id.text_firstNum);
        textView_secondNum = findViewById(R.id.text_secondNum);
        textView_score = findViewById(R.id.text_score);

        styleMap = generateQA.getQAStyle();
        bgColor = ContextCompat.getColor(this, styleMap.get("bgColor"));
        bgImage = styleMap.get("bgImage");

        textView_questionSymbol.setText(styleMap.get("symbol"));
        layout.setBackgroundResource(bgImage);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(bgColor));

        changeTypeFace(styleMap.get("title"));

        setQuestionView();
        setAnswerView();

    }

    private void changeTypeFace(int title) {
        TextView textView = styleUtils.getActionBarCustomTitleView(title, R.color.colorWhite, "casual");
        // Set the ActionBar display option
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        getSupportActionBar().setCustomView(textView);
    }

    private void setQuestionView() {
        generateQA.setQuestionView(textView_firstNum, textView_secondNum);
    }

    private void setAnswerView() {
        generateQA.setAnswersView(answerLayout);
    }

    private void playButtonSound (View view) {

        stopButtonSound();

        if (generateQA.isAnswerCorrectly(view) == true) {
            mediaPlayer =  MediaPlayer.create(this, R.raw.correct_answer);
        } else {
            mediaPlayer =  MediaPlayer.create(this, R.raw.wrong_answer);
        }

        mediaPlayer.start();
    }

    private void stopButtonSound() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void checkAnswer (View view) {

        if (generateQA.getIsAnsweredCorrectly() == true) {
            return;
        }

        playButtonSound(view);

        generateQA.setAnsweredView(view, true);
        generateQA.setProgressTextView(textView_score);
    }

    public void nextQuestion (View view) {

        if (generateQA.getIsAnswered() == false) {
            generateQA.setProgressTextView(textView_score);
        }

        stopButtonSound();
        mediaPlayer =  MediaPlayer.create(this, R.raw.next_question);
        mediaPlayer.start();

        setQuestionView();
        setAnswerView();
    }
}
