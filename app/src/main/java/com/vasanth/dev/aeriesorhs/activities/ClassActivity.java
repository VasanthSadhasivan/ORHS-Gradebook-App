package com.vasanth.dev.aeriesorhs.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vasanth.dev.aeriesorhs.R;
import com.vasanth.dev.aeriesorhs.helpers.DataStorageAndParsing;
import com.vasanth.dev.aeriesorhs.objects.Assignment;
import com.vasanth.dev.aeriesorhs.objects.Class;

import java.text.DecimalFormat;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class ClassActivity extends AppCompatActivity {
    Class classMain;
    public final String TAG = "ClassActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_main);
        Intent intent = getIntent();
        int class_index = intent.getIntExtra("class_index", -1);
        classMain = DataStorageAndParsing.classesAsArrayList.get(class_index);
        ((TextView) findViewById(R.id.ClassTitle)).setText(DataStorageAndParsing.classesAsArrayList.get(class_index).getName());
        setColor();
        addAssignments();
    }

    public void setColor() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_assignment_layout);
        linearLayout.setBackgroundColor(classMain.getColor());
    }

    public void addAssignments() {
        ScrollView sv = (ScrollView) findViewById(R.id.scrollView2);
        sv.setBackgroundColor(classMain.getColor());

        LinearLayout linearLayoutOutside = new LinearLayout(this);
        linearLayoutOutside.setOrientation(LinearLayout.VERTICAL);
        linearLayoutOutside.setBackgroundColor(classMain.getColor());

        for (Assignment a : classMain.getAssignments()) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setBackgroundColor(classMain.getColor());

            linearLayout.setPadding(0, 10, 0, 10);
            linearLayout.setWeightSum(5);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setBackgroundColor(classMain.getColor());
            TextView a_name = new TextView(this);
            TextView a_whatYouGot = new TextView(this);
            TextView a_percentage = new TextView(this);
            a_name.setTextColor(Color.WHITE);
            a_percentage.setTextColor(Color.WHITE);
            a_whatYouGot.setTextColor(Color.WHITE);
            a_name.setPadding(10, 10, 0, 10);
            a_percentage.setPadding(10, 10, 0, 10);
            a_whatYouGot.setPadding(10, 10, 0, 10);
            a_name.setTextSize(20);
            a_percentage.setTextSize(15);
            a_whatYouGot.setTextSize(15);
            a_name.setBackgroundColor(classMain.getColor());
            a_whatYouGot.setBackgroundColor(classMain.getColor());
            a_percentage.setBackgroundColor(classMain.getColor());
            a_name.setBackgroundResource(R.drawable.custom_border);
            a_percentage.setBackgroundResource(R.drawable.custom_border);
            a_whatYouGot.setBackgroundResource(R.drawable.custom_border);
            a_name.setText(a.getName() + "");
            a_whatYouGot.setText(a.getWhatYouGot() + "/" + a.getTotal());
            a_percentage.setText(df.format(a.getPercentage() * 100) + "%");
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.2f);
            a_name.setLayoutParams(param);
            param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.9f);
            a_whatYouGot.setLayoutParams(param);
            a_percentage.setLayoutParams(param);

            linearLayout.addView(a_name);
            linearLayout.addView(a_whatYouGot);
            linearLayout.addView(a_percentage);

            linearLayoutOutside.addView(linearLayout);
            Log.w(TAG, "Got through one assignment iteration");
        }
        sv.addView(linearLayoutOutside);
    }


}
