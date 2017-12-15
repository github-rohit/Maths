package com.nirmalrohit.maths;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Quizzes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);

        TextView textView = new TextView(getApplicationContext());

        // Create a LayoutParams for TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        textView.setLayoutParams(lp);
        textView.setText(R.string.select_category); // ActionBar title text

        // Set the text color of TextView to black
        // Set the monospace font for TextView text
        // This will change ActionBar title text font
        Typeface typeface = Typeface.create("casual", Typeface.BOLD);
        textView.setTypeface(typeface);
        textView.setTextColor(getResources().getColor(R.color.coloBlack));
        textView.setTextSize(20);
        // Set the ActionBar display option
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        getSupportActionBar().setCustomView(textView);
    }

    public void selectQuizModule(View view) {
        int index = Integer.parseInt( view.getTag().toString() );
        Intent intent = new Intent(Quizzes.this, Level.class);
        intent.putExtra("type", index);
        intent.putExtra("quiz", true);

        //System.out.println("TYPE INDEX: " + index);

        startActivity(intent);
    }

    public void doNothing(View view){

    }
}
