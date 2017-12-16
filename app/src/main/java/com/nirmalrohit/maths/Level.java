package com.nirmalrohit.maths;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

public class Level extends Dialog implements android.view.View.OnClickListener {
    private Activity activity;
    private int categoryType;
    private Boolean isQuiz;

    public Level (Activity act, int index, Boolean quiz) {
        super(act);

        this.activity = act;
        this.categoryType = index;
        this.isQuiz = quiz;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.activity_level);

        GridLayout gridLayout = findViewById(R.id.gridLayoutLevel);

        for(int i = 0 ; i < gridLayout.getChildCount() ; i++){
            Button button = (Button) gridLayout.getChildAt(i);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startLearning(view);
                }
            });
        }
    }

    public void startLearning (View view) {

        int max = Integer.parseInt( view.getTag().toString() );
        Intent intent;

        if (isQuiz == true) {
            intent = new Intent(activity, Quiz.class);
        } else {
            intent = new Intent(activity, Calculus.class);
        }

        intent.putExtra("type", categoryType);
        intent.putExtra("max", max);

        this.activity.startActivity(intent);
        dismiss();

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
