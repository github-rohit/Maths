package com.nirmalrohit.maths;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ProgressCard extends AppCompatActivity {
    private DBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_card);

        dbUtils = new DBUtils(this);
        StyleUtils styleUtils = new StyleUtils(this);

        TextView textView = styleUtils.getActionBarCustomTitleView(R.string.your_progress_card, R.color.coloBlack, "casual");

        // Set the ActionBar display option
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        getSupportActionBar().setCustomView(textView);

        populateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_score, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        dbUtils.clearAll();
        finish();
        return true;
    }

    private void populateList() {
        final ArrayList<HashMap<String, Object>> progressCardList = dbUtils.getAll();

        ListView list = (ListView) findViewById(R.id.listView_results);
        ProgressCardListAdapter progressCardListAdapter = new ProgressCardListAdapter(this, progressCardList);

        list.setAdapter(progressCardListAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, Object> item = progressCardList.get(i);

                ArrayList arrayList = (ArrayList) item.get("questionList");
                showResult(arrayList);
            }
        });
    }

    public void showResult (ArrayList questionList) {

        try {
            AnswerListAdapter answerListAdapter = new AnswerListAdapter(ProgressCard.this, questionList);

            AlertDialog.Builder builder = new AlertDialog.Builder(ProgressCard.this)
                    .setTitle(R.string.your_answers)
                    .setAdapter(answerListAdapter, null)
                    .setCancelable(true)
                    .setNegativeButton("Close", null);

            AlertDialog alertDialogObject = builder.create();
        ListView listView = alertDialogObject.getListView();
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.coloBlack20))); // set color
        listView.setDividerHeight(1); // set height
            alertDialogObject.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
