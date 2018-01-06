package com.nirmalrohit.maths;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rohitgautam on 05/01/18.
 */

public class ProgressCardListAdapter extends BaseAdapter {

    private Context mContext;
    ArrayList<HashMap<String, Object>> progressCardList;

    public ProgressCardListAdapter(Context context, ArrayList cur) {
        super();
        mContext = context;
        progressCardList = cur;
    }

    private class ViewHolder {
        TextView viewCat;
        TextView viewLevel;
        TextView viewDate;
        TextView viewScore;
        TextView viewCAnswer;
        TextView viewWAnswer;
    }

    @Override
    public int getCount() {
        return progressCardList.size();
    }

    @Override
    public HashMap getItem(int position) {
        return progressCardList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if ( convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.progress_card_row, viewGroup, false);

            final ViewHolder holder = new ViewHolder();

            holder.viewCat = (TextView) convertView.findViewById(R.id.textView_cat);
            holder.viewLevel = (TextView) convertView.findViewById(R.id.textView_level);
            holder.viewDate = (TextView) convertView.findViewById(R.id.textView_date);
            holder.viewScore = (TextView) convertView.findViewById(R.id.textView_score);
            holder.viewCAnswer = (TextView) convertView.findViewById(R.id.textView_answerCorrect);
            holder.viewWAnswer = (TextView) convertView.findViewById(R.id.textView_wrongAnswer);

            convertView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();
        HashMap<String, Object> item = getItem(position);

        holder.viewCat.setText((String) item.get("catType"));
        holder.viewLevel.setText((String) item.get("level"));
        holder.viewDate.setText((String) item.get("createdAt"));
        holder.viewScore.setText((String) item.get("score"));
        holder.viewCAnswer.setText((String) item.get("correctAnswer"));
        holder.viewWAnswer.setText((String) item.get("wrongAnswer"));


        return convertView;
    }
}
