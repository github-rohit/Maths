package com.nirmalrohit.maths;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by rohit on 12/1/2017.
 */

public class GenerateQA {

    static int MAX = 20;
    static int TYPE = 1;

    private final int DRAW_RIPPLE_EFFECT = R.drawable.ripple_effect;
    private final int MAX_ANS = 4;
    private final int MAX_Test = 4;

    private ArrayList<Integer> question = new ArrayList<Integer>();
    private ArrayList<Integer> answers = new ArrayList<Integer>();
    private Random random;
    private int correctAnsIndex;
    private int mTotalQuestions;
    private int mTotalCorrectAnswers;

    private Boolean mIsAnswered;
    private Boolean mIsAnsweredCorrectly;

    public GenerateQA () {

        random = new Random();
        mTotalQuestions = 0;
        mTotalCorrectAnswers = 0;
    }

    public int getTotalQuestions() {
        return mTotalQuestions;
    }

    public void setTotalQuestions(int mTotalQuestions) {
        this.mTotalQuestions = mTotalQuestions;
    }

    public int getTotalCorrectAnswers() {
        return mTotalCorrectAnswers;
    }

    public void setTotalCorrectAnswers(int mTotalCorrectAnswers) {
        this.mTotalCorrectAnswers = mTotalCorrectAnswers;
    }

    public Boolean getIsAnswered() {
        return mIsAnswered;
    }

    public Boolean getIsAnsweredCorrectly() {
        return mIsAnsweredCorrectly;
    }

    public void setIsAnsweredCorrectly(Boolean mIsAnsweredCorrectly) {
        this.mIsAnsweredCorrectly = mIsAnsweredCorrectly;
    }

    public void setIsAnswered(Boolean mIsAnswered) {
        this.mIsAnswered = mIsAnswered;
    }

    public ArrayList getQuestion() {
        int firstNum = random.nextInt(MAX) + 1;
        int secondNum = random.nextInt(MAX);

        if (firstNum < MAX_ANS) {
            while (firstNum < MAX_ANS) {
                firstNum = random.nextInt(MAX) + 1;
            }
        }

        if (TYPE == 2) {
            secondNum = random.nextInt(firstNum);
        } else if (TYPE == 4) {

            if (secondNum == 0) {
                secondNum = 1;
            }

            firstNum = firstNum * secondNum;
        }

        question.clear();

        question.add(0, firstNum);
        question.add(1, secondNum);

        setIsAnsweredCorrectly(false);
        setIsAnswered(false);

        mTotalQuestions++;

        return question;
    }

    public void setQuestionView(TextView textView_firstNum, TextView textView_secondNum) {

        getQuestion();

        textView_firstNum.setText(Integer.toString(question.get(0)));
        textView_secondNum.setText(Integer.toString(question.get(1)));

    }

    public ArrayList getAnswers() {
        int answer = getCorrectAnswer();

        correctAnsIndex = random.nextInt(MAX_ANS);
        answers.clear();

        for (int i = 0; i < MAX_ANS; i++) {
            int num;
            if (i == correctAnsIndex) {
                num = answer;
            } else {
                num = answer + random.nextInt(MAX_ANS) - random.nextInt(MAX_ANS);

                while (num == answer || answers.indexOf(num) != -1 || num < 0) {
                    num = answer + random.nextInt(MAX_ANS) - random.nextInt(MAX_ANS);
                }
            }

            answers.add(i, num);
        }

        return answers;
    }

    public void setAnswersView(GridLayout answerLayout) {
        int count = answerLayout.getChildCount();

        getAnswers();

        for(int i = 0 ; i <count ; i++){
            TextView child = (TextView) answerLayout.getChildAt(i);
            child.setAlpha(1);
            child.setText(answers.get(i).toString());
            child.setBackgroundResource(DRAW_RIPPLE_EFFECT);
        }
    }

    public Boolean isCorrectAnswer(int index) {
        Boolean isAnswer;

        if (index == correctAnsIndex) {
            setIsAnsweredCorrectly(true);

            if (getIsAnswered() == false) {
                mTotalCorrectAnswers++;
            }

            isAnswer = true;
        } else {
            isAnswer = false;
        }
        setIsAnswered(true);
        return isAnswer;
    }

    public HashMap getQAStyle() {

        HashMap<String, Integer> style = new HashMap<String, Integer>();
        int symbol = R.string.symbol_addition;
        int bgColor = R.color.colorTwo;
        int title = R.string.addition;

        if (TYPE == 1) {
            symbol = R.string.symbol_addition;
            bgColor = R.color.colorTwo;
            title = R.string.addition;
        } else if (TYPE == 2) {
            symbol = R.string.symbol_subtraction;
            bgColor = R.color.colorThree;
            title = R.string.subtraction;
        } else if (TYPE == 3) {
            symbol = R.string.symbol_multiplication;
            bgColor = R.color.colorFour;
            title = R.string.multiplication;
        } else if (TYPE == 4) {
            symbol = R.string.symbol_divide;
            bgColor = R.color.colorFive;
            title = R.string.divide;
        }

        style.clear();

        style.put("symbol", symbol);
        style.put("bgColor", bgColor);
        style.put("title", title);

        return style;
    }

    public void setAnsweredView (View view, Boolean animate) {
        TextView textView = (TextView) view;
        int index = Integer.parseInt( view.getTag().toString() );
        int bgColor;
        Boolean isAnswer = isCorrectAnswer(index);

        if (isAnswer) {
            bgColor = R.drawable.green_button_smile;
            hideAnswerButtons(view, animate);
        } else {
            bgColor = R.drawable.red_button;
        }

        if (animate) {
            flipView(textView, 100, bgColor);
        } else {
            textView.setText("");
            view.setBackgroundResource(bgColor);
        }


    }

    public void setProgressTextView(TextView view) {
        String text = Integer.toString(getTotalCorrectAnswers()) + "/" + Integer.toString(getTotalQuestions());

        view.setText(text);
    }

    private int getCorrectAnswer() {
        int answer = 0;

        if (TYPE == 1) {
            answer = question.get(0) + question.get(1);
        } else if (TYPE == 2) {
            answer = question.get(0) - question.get(1);
        } else if (TYPE == 3) {
            answer = question.get(0) * question.get(1);
        } else if (TYPE == 4) {
            answer = question.get(0) / question.get(1);
        }

        return answer;
    }

    private void hideAnswerButtons (View view, Boolean animate) {
        ViewGroup parent = (ViewGroup) view.getParent();

        for(int i = 0 ; i < parent.getChildCount() ; i++){
            TextView child = (TextView) parent.getChildAt(i);

            if (view != child) {
                if (animate == true) {
                    child.animate().alpha(.1f).setDuration(100).setListener(null);
                } else {
                    child.setAlpha(.1f);
                }
            }

        }

    }

    private void flipView (final TextView view, final int duration, final int bgColor) {

        view.animate().scaleX(0).setDuration(duration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setText("");
                view.setBackgroundResource(bgColor);
                view.animate().scaleX(1).setDuration(duration).setListener(null);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
    }
}
