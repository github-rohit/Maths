package com.nirmalrohit.maths;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by rohit on 12/16/2017.
 */

public class StyleUtils extends AppCompatActivity {

    private Activity activity;

    public StyleUtils (Activity activity) {
        super();
        this.activity = activity;
    }

    public TextView getActionBarCustomTitleView(int titleId, int colorId, String fontFamily) {
        TextView textView = new TextView(activity);

        // Create a LayoutParams for TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        textView.setLayoutParams(lp);
        textView.setText(titleId); // ActionBar title text

        // Set the text color of TextView to black
        // Set the monospace font for TextView text
        // This will change ActionBar title text font

        if (fontFamily != null) {
            Typeface typeface = Typeface.create(fontFamily, Typeface.BOLD);
            textView.setTypeface(typeface);
        }

        if (colorId != 0) {
            textView.setTextColor(activity.getResources().getColor(colorId));
        }


        textView.setTextSize(20);

        return textView;
    }

}
