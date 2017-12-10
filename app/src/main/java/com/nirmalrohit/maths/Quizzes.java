package com.nirmalrohit.maths;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Quizzes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);
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
