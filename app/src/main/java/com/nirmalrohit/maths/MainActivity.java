package com.nirmalrohit.maths;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Level dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StyleUtils styleUtils = new StyleUtils(this);
        TextView textView = styleUtils.getActionBarCustomTitleView(R.string.select_category, R.color.coloBlack, "casual");
        // Set the ActionBar display option
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        getSupportActionBar().setCustomView(textView);

    }

    public void selectModule(View view) {
        int index = Integer.parseInt( view.getTag().toString() );
        Intent intent;

        if (index == 0 || index == 5) {

            if (index == 0) {
                intent = new Intent(MainActivity.this, TimesTables.class);
            } else {
                intent = new Intent(MainActivity.this, Quizzes.class);
            }

            intent.putExtra("type", index);
            startActivity(intent);

        } else {
            dialog = new Level(this, index, false);
            dialog.show();
        }
    }

}

