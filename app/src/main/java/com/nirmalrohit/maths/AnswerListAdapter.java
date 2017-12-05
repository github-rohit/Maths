package com.nirmalrohit.maths;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rohit on 12/5/2017.
 */

public class AnswerListAdapter extends BaseAdapter {

    private Context mContext;
    ArrayList<String> qAList;

    public AnswerListAdapter(Context context, ArrayList cur) {
        super();
        mContext = context;
        qAList = cur;
    }

    private class ViewHolder {
        TextView author;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    @Override
    public int getCount() {
        return qAList.size();
    }

    @Override
    public String getItem(int position) {
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
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.author.getLayoutParams();
            convertView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.author.setText(Integer.toString(position) + " " + getItem(position));
        //holder.body.setText(Integer.toString(qAList.get(position)));

        return convertView;
    }
}
