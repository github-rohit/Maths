package com.nirmalrohit.maths;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Quizzes extends AppCompatActivity {

    private Level dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);

        StyleUtils styleUtils = new StyleUtils(this);
        TextView textView = styleUtils.getActionBarCustomTitleView(R.string.select_quiz_category, R.color.coloBlack, "casual");

        // Set the ActionBar display option
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        getSupportActionBar().setCustomView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_score, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(Quizzes.this, ProgressCard.class);
        startActivity(intent);

        return true;
    }

    public void selectQuizModule(View view) {
        int index = Integer.parseInt( view.getTag().toString() );

        dialog = new Level(this, index, true);
        dialog.show();
    }

    public void doNothing(View view){

    }
}
