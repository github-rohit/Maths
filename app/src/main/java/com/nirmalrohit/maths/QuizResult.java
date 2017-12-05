package com.nirmalrohit.maths;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizResult extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        questionList = (ArrayList<HashMap<String, Object>>) getIntent().getSerializableExtra("questions");
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
}
