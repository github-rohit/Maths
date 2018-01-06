package com.nirmalrohit.maths;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by rohitgautam on 05/01/18.
 */

public class DBUtils {
    private final String DB_NAME = "Maths";

    private SQLiteDatabase mDatabase;
    private Activity mActivity;

    DBUtils(Activity activity) {
        mActivity = activity;
        try {
            // mActivity.deleteDatabase(DB_NAME);
            mDatabase = mActivity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
            mDatabase.execSQL("CREATE TABLE IF NOT EXISTS result (cat_type VARCHAR, level VARCHAR , correct_answer INT(3), wrong_answer INT(3), score INT(3), question_list BLOB, created_at DATETIME DEFAULT CURRENT_TIMESTAMP)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(String type, String level, int correctAnswer, int wrongAnswer, int score, String questionList) {
        JSONObject json = new JSONObject();
        try {
            json.put("questionList", new JSONArray(questionList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String arrayList = json.toString();

        try {
            mDatabase.execSQL("INSERT INTO result (cat_type, level, correct_answer, wrong_answer, score, question_list) VALUES ('" + type + "','" + level + "'," + correctAnswer + "," +  wrongAnswer + "," + score + ",'" + arrayList + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<HashMap<String, Object>> getAll () {
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();

        try {
            Cursor cursor = mDatabase.rawQuery("SELECT * FROM result", null);

            int typeIndex = cursor.getColumnIndex("cat_type");
            int level = cursor.getColumnIndex("level");
            int correctAnswer = cursor.getColumnIndex("correct_answer");
            int wrongAnswer = cursor.getColumnIndex("wrong_answer");
            int score = cursor.getColumnIndex("score");
            int questionList = cursor.getColumnIndex("question_list");
            int createdAt = cursor.getColumnIndex("created_at");

            cursor.moveToFirst();

            if (cursor != null) {
                do {
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("catType", cursor.getString(typeIndex));
                    hashMap.put("level", cursor.getString(level));
                    hashMap.put("correctAnswer", Integer.toString(cursor.getInt(correctAnswer)));
                    hashMap.put("wrongAnswer", Integer.toString(cursor.getInt(wrongAnswer)));
                    hashMap.put("score", Integer.toString(cursor.getInt(score)));
                    hashMap.put("questionList", convertToArrayList(cursor.getString(questionList)));
                    hashMap.put("createdAt", cursor.getString(createdAt));

                    arrayList.add(hashMap);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    public void clearAll() {
        mActivity.deleteDatabase(DB_NAME);
    }

    public void print() {
        try {
            Cursor c = mDatabase.rawQuery("SELECT * FROM result", null);

            int typeIndex = c.getColumnIndex("cat_type");
            int level = c.getColumnIndex("level");
            int correctAnswer = c.getColumnIndex("correct_answer");
            int wrongAnswer = c.getColumnIndex("wrong_answer");
            int score = c.getColumnIndex("score");
            int questionList = c.getColumnIndex("question_list");
            int createdAt = c.getColumnIndex("created_at");

            c.moveToFirst();

            if (c != null) {
                do {

                    Log.i("type", c.getString(typeIndex));
                    Log.i("type correctAnswer", c.getString(level));
                    Log.i("type correctAnswer", Integer.toString(c.getInt(correctAnswer)));
                    Log.i("type wrongAnswer", Integer.toString(c.getInt(wrongAnswer)));
                    Log.i("type score", Integer.toString(c.getInt(score)));
                    Log.i("type questionList", c.getString(questionList));
                    Log.i("type createdAt", c.getString(createdAt));
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ArrayList convertToArrayList(String questionStr) {
        ArrayList<HashMap<String, Object>> listdata = new ArrayList<>();
        JSONObject json = null;

        try {
            json = new JSONObject(questionStr);
            JSONArray questionList = json.optJSONArray("questionList");

            if (questionList != null) {
                for (int i=0;i<questionList.length();i++){
                    HashMap<String, Object> map = new HashMap<>();
                    JSONObject jObject = new JSONObject(questionList.getString(i));
                    Iterator<?> keys = jObject.keys();

                    while( keys.hasNext() ){
                        String key = (String)keys.next();
                        String value = jObject.getString(key);

                        if (key.equals( "your_answer" ) || key.equals( "correctAnswer" )) {
                            map.put(key, Integer.parseInt(value));
                        } else {
                            map.put(key, convertToArray(value));
                        }


                    }

                    listdata.add(map);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listdata;
    }

    private ArrayList convertToArray(String str) {

        String[] items = str.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        ArrayList<Integer> results = new ArrayList<Integer>();

        for (int i = 0; i < items.length; i++) {
            try {
                results.add(Integer.parseInt(items[i]));
            } catch (NumberFormatException nfe) {
                //NOTE: write something here if you need to recover from formatting errors
            };
        }

        return results;
    }

}
