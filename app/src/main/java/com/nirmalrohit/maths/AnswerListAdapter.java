package com.nirmalrohit.maths;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by rohit on 12/5/2017.
 */

public class AnswerListAdapter extends BaseAdapter {

    private Context mContext;
    ArrayList<HashMap<String, Object>> qAList;

    public AnswerListAdapter(Context context, ArrayList cur) {
        super();
        mContext = context;
        qAList = cur;
    }

    private class ViewHolder {
        TextView question;
        TextView answer;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return qAList.size();
    }

    @Override
    public HashMap getItem(int position) {
        return qAList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if ( convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_answers_list, viewGroup, false);

            final ViewHolder holder = new ViewHolder();
            holder.question = (TextView) convertView.findViewById(R.id.textView_question);
            holder.answer = (TextView) convertView.findViewById(R.id.textView_answer);
            holder.params = (LinearLayout.LayoutParams) holder.question.getLayoutParams();
            convertView.setTag(holder);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        HashMap<String, Object> item = getItem(position);

        holder.question.setText(position + 1 + ". " + getQuestion(item));
        holder.answer.setText(" " + getUserAnswer(item));
        setQAStyle(item, holder);

        return convertView;
    }

    private String getQuestion (HashMap<String, Object> item) {
        ArrayList<Integer> question = (ArrayList<Integer>) item.get("question");

        return Integer.toString(question.get(0)) + " " + mContext.getResources().getString(question.get(2)) + " " + Integer.toString(question.get(1)) + " = " + getCorrectAnswer(item);
    }

    private String getCorrectAnswer (HashMap<String, Object> item) {
        Integer correctAnswer = (Integer) item.get("correctAnswer");

        return Integer.toString(correctAnswer);
    }

    private String getUserAnswer (HashMap<String, Object> item) {
        ArrayList<Integer> answers = (ArrayList<Integer>) item.get("answers");
        Integer correctAnswer = (Integer) item.get("your_answer");

        if (correctAnswer != null) {
            return Integer.toString(answers.get(correctAnswer));
        } else {
            return "";
        }


    }

    private void setQAStyle (HashMap<String, Object> item, ViewHolder holder) {
        String correctAnswer = getCorrectAnswer(item);
        String yourAnswerIndex = getUserAnswer(item);

        if (!Objects.equals(yourAnswerIndex, correctAnswer)) {
            holder.question.setTextColor(mContext.getResources().getColor(R.color.coloWrongAns));
            holder.answer.setTextColor(mContext.getResources().getColor(R.color.coloWrongAns));
        }

        holder.question.setLayoutParams(holder.params);
        holder.answer.setLayoutParams(holder.params);

    }

}
