package com.nirmalrohit.maths;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class QuizResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
    }

    public void showResult (View view) {
        ArrayList<String> items = new ArrayList<String>();

        for (int i = 1; i <= 10; i++ ) {
            String step = 2 + " X " + i + " = " + 2*i;
            items.add(step);
        }

        AnswerListAdapter adp = new AnswerListAdapter(this, items);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("ANSWER")
                .setAdapter(adp, null)
                .setCancelable(true)
                .setNegativeButton("Close", null);

        AlertDialog alertDialogObject = builder.create();
        ListView listView = alertDialogObject.getListView();
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.coloBlack20))); // set color
        listView.setDividerHeight(1); // set height
        alertDialogObject.show();
    }
}
