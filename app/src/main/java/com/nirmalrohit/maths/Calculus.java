package com.nirmalrohit.maths;

import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    private int unanswered;
    private Boolean isAttempted;
    private Boolean isAnswered;
    private Boolean isTest;
    private Boolean isRestart;

    private RelativeLayout startGameLayout;
    private RelativeLayout calculusLayout;
    private RelativeLayout relativeLayout;

    private LinearLayout resultLayout;

    private TextView textViewFinalScore;
    private TextView textSymbol;
    private TextView viewFirstNum;
    private TextView viewSecondNum;
    private TextView viewScore;
    private TextView viewTimer;

    private Button btnNext;
    private Button btnStartGame;

    private GridLayout gridLayout;
    private GridLayout gridLayoutLevel;

    private ArrayList<Integer> answers = new ArrayList<Integer>();

    private  Toast currentToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        calculusType = getIntent().getExtras().getInt("type");

        if (calculusType == 4) {
            setTitle(R.string.quiz);
        }

        isRestart = false;
        totalCorrectAns = 0;
        totalQuestion = 0;

        random = new Random();

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        calculusLayout = (RelativeLayout) findViewById(R.id.relativeLayoutCalculus);
        startGameLayout = (RelativeLayout) findViewById(R.id.relativeLayoutStartGame);

        textSymbol = (TextView) findViewById(R.id.text_symbol);

        viewFirstNum = (TextView) findViewById(R.id.text_number_one);
        viewSecondNum = (TextView) findViewById(R.id.text_number_two);
        viewTimer = (TextView) findViewById(R.id.textViewClock);
        viewScore = (TextView) findViewById(R.id.textViewScore);

        gridLayout = (GridLayout) findViewById(R.id.gridLayoutAnswers);
        gridLayoutLevel = (GridLayout) findViewById(R.id.gridLayoutLevel);

        btnStartGame = (Button) findViewById(R.id.buttonStartGame);
        btnNext = (Button) findViewById(R.id.button_next);

        if (calculusType == 4) {
            isTest = true;

            resultLayout = (LinearLayout) findViewById(R.id.resultLayout);
            textViewFinalScore = (TextView) findViewById(R.id.textViewFinalScore);

            calculusLayout.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);

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
        unanswered = 1;
        totalQuestion++;
        updateScoreText(viewScore);
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
                }
            }

            answers.add(i, num);
        }

        int count = gridLayout.getChildCount();

        for(int i = 0 ; i <count ; i++){
            TextView child = (TextView) gridLayout.getChildAt(i);
            child.setText(answers.get(i).toString());
            child.setBackgroundColor(getResources().getColor(R.color.coloBlack30));
        }

    }

    public void checkAnswer (View view) {

        unanswered = 0;

        int toastMsgId = R.string.wrong;
        int bgColor;

        if (isAnswered == false) {
            TextView textView = (TextView) view;
            int index = Integer.parseInt( view.getTag().toString() );
            int userAns = answers.get(index);

            if (userAns == correctAns) {
                isAnswered = true;
                toastMsgId = R.string.correct;
                bgColor = getResources().getColor(R.color.coloCorrectAns);
                textView.setBackgroundColor(bgColor);

            } else {
                isAttempted = true;
                bgColor = getResources().getColor(R.color.coloWrongAns);
                textView.setBackgroundColor(bgColor);
            }

            if (!isAttempted) {
                totalCorrectAns++;
            }

            showToastMsg(toastMsgId, bgColor);

            updateScoreText(viewScore);
        }

        if (isTest == true) {
            changeStyle();
            createQuestion();
        }

    }

    private void changeStyle () {

        int bgColorId = R.color.colorOne;
        int headingId = R.string.quiz;
        int symbol = R.string.symbol_addition;

        if (isTest == true) {
            calculusType =  random.nextInt(MAX_Test);
        }

        if (calculusType == 0) {
            bgColorId = R.color.colorTwo;
            headingId = R.string.addition;
            symbol = R.string.symbol_addition;

        } else if (calculusType == 1) {
            bgColorId = R.color.colorThree;
            headingId = R.string.subtraction;
            symbol = R.string.symbol_subtraction;

        } else if (calculusType == 2) {
            bgColorId = R.color.colorFour;
            headingId = R.string.multiplication;
            symbol = R.string.symbol_multiplication;

        } else if (calculusType == 3) {
            bgColorId = R.color.colorFive;
            headingId = R.string.divide;
            symbol = R.string.symbol_divide;
        }


        int bgColor = ContextCompat.getColor(this, bgColorId);

        relativeLayout.setBackgroundColor(bgColor);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(bgColor));
        setTitle(headingId);
        textSymbol.setText(symbol);
    }

    public void startGame (View view) {


        if (isRestart == true) {
            isRestart = false;
            btnStartGame.setText(R.string.tap_to_start);
            gridLayoutLevel.setVisibility(View.VISIBLE);
            resultLayout.setVisibility(View.GONE);
        } else {
            startGameLayout.setVisibility(View.GONE);
            calculusLayout.setVisibility(View.VISIBLE);

            if (isTest == true) {

                totalCorrectAns = 0;
                totalQuestion = 0;

                updateScoreText(viewScore);

                changeStyle();
                createQuestion();
                timer();
            }
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
                int bgColor = ContextCompat.getColor(Calculus.this, R.color.colorOne);

                relativeLayout.setBackgroundColor(bgColor);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(bgColor));
                setTitle(R.string.quiz);

                currentToast.cancel();
                btnStartGame.setText(R.string.tap_to_restart);

                isRestart = true;

                gridLayoutLevel.setVisibility(View.GONE);
                resultLayout.setVisibility(View.VISIBLE);
                startGameLayout.setVisibility(View.VISIBLE);

                calculusLayout.setVisibility(View.GONE);

                updateScoreText(textViewFinalScore);

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

    private void updateScoreText (TextView view) {
        view.setText(Integer.toString(totalCorrectAns) + "/" + Integer.toString(totalQuestion - unanswered));
    }

    private void showToastMsg (int msg, int bg) {

        if(currentToast == null) {
            currentToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }

        currentToast.setText(msg);
        currentToast.setDuration(Toast.LENGTH_LONG);
        currentToast.show();
    }
}
