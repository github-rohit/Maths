package com.nirmalrohit.maths;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Calculus extends AppCompatActivity {

    private final int MAX_INT_SMALL_SUB = 5;
    private final int MAX_INT_SMALL_MUL = 11;
    private final int MAX_ANS = 4;
    private final int MAX_Test = 4;

    private int MAX_INT = 15;

    private int calculusType;
    private Random random;
    private int firstNum;
    private int secondNum;
    private int correctAnsPos;
    private int correctAns;
    private int totalCorrectAns;
    private int totalQuestion;
    private Boolean isAttempted;
    private Boolean isAnswered;
    private Boolean isTest;

    private RelativeLayout startGameLayout;
    private RelativeLayout calculusLayout;
    private RelativeLayout relativeLayout;

    private TextView textViewScoreLabel;
    private TextView textViewFinalScore;
    private TextView heading;
    private TextView textSymbol;
    private TextView viewFirstNum;
    private TextView viewSecondNum;
    private TextView viewScore;
    private TextView viewTimer;

    private ImageView result;

    private Button btnNext;

    private GridLayout gridLayout;
    private GridLayout gridLayoutLevel;

    private ArrayList<Integer> answers = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        calculusType = getIntent().getExtras().getInt("type");

        totalCorrectAns = 0;
        totalQuestion = 0;

        random = new Random();

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        calculusLayout = (RelativeLayout) findViewById(R.id.relativeLayoutCalculus);
        startGameLayout = (RelativeLayout) findViewById(R.id.relativeLayoutStartGame);

        heading = (TextView) findViewById(R.id.textViewHeading);
        textSymbol = (TextView) findViewById(R.id.text_symbol);

        viewFirstNum = (TextView) findViewById(R.id.text_number_one);
        viewSecondNum = (TextView) findViewById(R.id.text_number_two);
        result = (ImageView) findViewById(R.id.imageViewResult);
        viewTimer = (TextView) findViewById(R.id.textViewClock);
        viewScore = (TextView) findViewById(R.id.textViewScore);

        gridLayout = (GridLayout) findViewById(R.id.gridLayoutAnswers);
        gridLayoutLevel = (GridLayout) findViewById(R.id.gridLayoutLevel);

        btnNext = (Button) findViewById(R.id.button_next);

        if (calculusType == 4) {
            isTest = true;

            textViewScoreLabel = (TextView) findViewById(R.id.textViewScoreLabel);
            textViewFinalScore = (TextView) findViewById(R.id.textViewFinalScore);

            calculusLayout.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            result.setVisibility(View.GONE);

            viewTimer.setVisibility(View.VISIBLE);
            startGameLayout.setVisibility(View.VISIBLE);

        } else {
            isTest = false;

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isTest == true) {
                        changeStyle();
                    }
                    createQuestion();
                }
            });

            changeStyle();
            createQuestion();
        }
    }

    private void createQuestion () {
        isAttempted = false;
        isAnswered = false;
        question();
        createAnswers();
        result.setImageResource(0);
        totalQuestion++;
    }

    private void question () {
        firstNum = random.nextInt(MAX_INT) + 1;
        secondNum = random.nextInt(MAX_INT);

        correctAns = correctAnswer();

        viewFirstNum.setText(Integer.toString(firstNum));
        viewSecondNum.setText(Integer.toString(secondNum));
    }

    private int correctAnswer () {

        int ans = firstNum + secondNum;

        if (calculusType == 0) {
            ans = firstNum + secondNum;
        } else if (calculusType == 1) {
            if (firstNum < MAX_INT_SMALL_SUB) {
                firstNum += MAX_INT_SMALL_SUB;
            }
            secondNum = random.nextInt(firstNum);
            ans = firstNum - secondNum;
        } else if (calculusType == 2) {
            secondNum = random.nextInt(MAX_INT_SMALL_MUL);
            ans = firstNum * secondNum;
        } else if (calculusType == 3) {

            if (secondNum == 0) {
                secondNum = 1;
            }

            firstNum = firstNum * secondNum;

            ans = firstNum / secondNum;
        }

        return ans;
    }

    private void createAnswers () {
        correctAnsPos = random.nextInt(MAX_ANS);
        answers.clear();

        for (int i = 0; i < MAX_ANS; i++) {
            int num;
            if (i == correctAnsPos) {
                num = correctAns;
            } else {
                num = correctAns + random.nextInt(MAX_ANS) - random.nextInt(MAX_ANS);

                while (num == correctAns || answers.indexOf(num) != -1 || num < 0) {
                    num = correctAns + random.nextInt(MAX_ANS) - random.nextInt(MAX_ANS);
                    Log.i("num re: ", Integer.toString(num));
                }
            }

            answers.add(i, num);
        }

        int count = gridLayout.getChildCount();

        for(int i = 0 ; i <count ; i++){
            TextView child = (TextView) gridLayout.getChildAt(i);
            child.setText(answers.get(i).toString());
            child.setBackgroundColor(getResources().getColor(R.color.coloBlack20));
        }

    }

    public void checkAnswer (View view) {

        if (isAnswered == false) {
            TextView textView = (TextView) view;
            int index = Integer.parseInt( view.getTag().toString() );
            int userAns = answers.get(index);

            if (userAns == correctAns) {
                isAnswered = true;
                result.setImageResource(R.drawable.correct);
                textView.setBackgroundColor(getResources().getColor(R.color.coloCorrectAns));

            } else {
                isAttempted = true;
                result.setImageResource(R.drawable.wrong);
                textView.setBackgroundColor(getResources().getColor(R.color.coloWrongAns));
            }

            if (!isAttempted) {
                totalCorrectAns++;
            }

            viewScore.setText(Integer.toString(totalCorrectAns) + "/" + Integer.toString(totalQuestion));
        }

        if (isTest == true) {
            changeStyle();
            createQuestion();
        }

    }

    private void changeStyle () {

        int bgColor = R.color.colorOne;
        int headingId = R.string.subtraction;
        int symbol = R.string.symbol_addition;

        if (isTest == true) {
            calculusType =  random.nextInt(MAX_Test);
        }

        if (calculusType == 0) {
            bgColor = R.color.colorTwo;
            headingId = R.string.addition;
            symbol = R.string.symbol_addition;

        } else if (calculusType == 1) {
            bgColor = R.color.colorThree;
            headingId = R.string.subtraction;
            symbol = R.string.symbol_subtraction;

        } else if (calculusType == 2) {
            bgColor = R.color.colorFour;
            headingId = R.string.multiplication;
            symbol = R.string.symbol_multiplication;

        } else if (calculusType == 3) {
            bgColor = R.color.colorFive;
            headingId = R.string.divide;
            symbol = R.string.symbol_divide;
        }

        relativeLayout.setBackgroundColor(getResources().getColor(bgColor));
        heading.setText(headingId);
        textSymbol.setText(symbol);
    }

    public void startGame (View view) {

        startGameLayout.setVisibility(View.GONE);
        calculusLayout.setVisibility(View.VISIBLE);

        if (isTest == true) {

            totalCorrectAns = 0;
            totalQuestion = 0;

            viewScore.setText(Integer.toString(totalCorrectAns) + "/" + Integer.toString(totalQuestion));

            changeStyle();
            createQuestion();
            timer();
        }


    }

    private void timer () {
        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                viewTimer.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {

                textViewScoreLabel.setVisibility(View.VISIBLE);
                textViewFinalScore.setVisibility(View.VISIBLE);
                startGameLayout.setVisibility(View.VISIBLE);

                calculusLayout.setVisibility(View.GONE);

                textViewFinalScore.setText(Integer.toString(totalCorrectAns) + "/" + Integer.toString(totalQuestion));

            }
        }.start();
    }

    public void setLevel (View view) {
        TextView textView = (TextView) view;

        MAX_INT = Integer.parseInt( view.getTag().toString() );

        int count = gridLayoutLevel.getChildCount();

        for(int i = 0 ; i <count ; i++){
            TextView child = (TextView) gridLayoutLevel.getChildAt(i);
            child.setBackgroundColor(getResources().getColor(R.color.coloBlack20));
        }

        textView.setBackgroundColor(getResources().getColor(R.color.coloCorrectAns));
    }


}
