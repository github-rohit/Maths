package com.nirmalrohit.maths;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizResult extends AppCompatActivity {
    private int max;
    private int type;
    private ArrayList<HashMap<String, Object>> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        TextView viewTotalScore = findViewById(R.id.textView_scoreTotal);
        TextView viewCorrectCount = findViewById(R.id.textView_answerCount);
        TextView viewWrongCount = findViewById(R.id.textView_answerWrong);
        TextView viewSkipCount = findViewById(R.id.textView_answerSkip);

        int correctCount = getIntent().getExtras().getInt("correctAnswerCount");
        int wrongCount = getIntent().getExtras().getInt("wrongAnswerCount");
        int skipCount = getIntent().getExtras().getInt("skipAnswerCount");

        max = getIntent().getExtras().getInt("max");
        type = getIntent().getExtras().getInt("type");

        questionList = (ArrayList<HashMap<String, Object>>) getIntent().getSerializableExtra("questions");

        viewTotalScore.setText(Integer.toString(max/5 * correctCount));

        viewCorrectCount.setText(Integer.toString(correctCount));
        viewWrongCount.setText(Integer.toString(wrongCount));
        viewSkipCount.setText(Integer.toString(skipCount));

        int lastIndex = questionList.size() - 1;
        HashMap<String, Object> lastItem = (HashMap<String, Object>) questionList.get(lastIndex);
        ArrayList<Integer> answers = (ArrayList<Integer>) lastItem.get("your_answer");

        if (answers == null) {
            questionList.remove(lastIndex);
        }

        if (questionList.size() == 0) {
            Button btn = (Button) findViewById(R.id.button_checkAnswers);
            btn.setEnabled(false);
            Drawable[] drawables = btn.getCompoundDrawables();
            drawables[1].setAlpha(110);
        }
    }

    public void restartQuiz (View view) {
        Intent intent = new Intent(QuizResult.this, Quiz.class);
        intent.putExtra("type", type);
        intent.putExtra("max", max);

        finish();

        startActivity(intent);

    }

    public void showResult (View view) {

        AnswerListAdapter answerListAdapter = new AnswerListAdapter(this, questionList);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.your_answers)
                .setAdapter(answerListAdapter, null)
                .setCancelable(true)
                .setNegativeButton("Close", null);

        AlertDialog alertDialogObject = builder.create();
        ListView listView = alertDialogObject.getListView();
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.coloBlack20))); // set color
        listView.setDividerHeight(1); // set height
        alertDialogObject.show();
    }

//    public void shareIt(View view) {
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AndroidSolved");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Now Learn Android with AndroidSolved clicke here to visit https://androidsolved.wordpress.com/ ");
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));
//    }

    public void close (View view) {
        finish();
    }
}
