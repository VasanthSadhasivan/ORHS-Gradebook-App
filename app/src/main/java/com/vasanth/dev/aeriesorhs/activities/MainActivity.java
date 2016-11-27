package com.vasanth.dev.aeriesorhs.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.vasanth.dev.aeriesorhs.R;
import com.vasanth.dev.aeriesorhs.async.GetAssignment;
import com.vasanth.dev.aeriesorhs.async.GetData;
import com.vasanth.dev.aeriesorhs.async.PostData;
import com.vasanth.dev.aeriesorhs.helpers.DataStorageAndParsing;
import com.vasanth.dev.aeriesorhs.objects.Class;

import java.util.ArrayList;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";
    public ArrayList<AsyncTask> asyncTasks = new ArrayList<AsyncTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataStorageAndParsing.setColors();
        asyncTasks.add(new PostData());
        asyncTasks.add(new GetData());
        asyncTasks.add(new GetAssignment());
        asyncTasks.get(0).execute(new String[]{"ayy"});
        asyncTasks.get(1).execute(new String[]{"ayy"});
        Log.v(TAG, "Classes Loading: " + DataStorageAndParsing.classesAsString);
        while (!GetData.finished) {
        }
        Log.v(TAG, "Classes Loaded");
        asyncTasks.get(2).execute(new String[]{});
        Log.v(TAG, "getASsignment.done: " + GetAssignment.done);
        DataStorageAndParsing.createClasses();
        addClassButtons();
        if (DataStorageAndParsing.classesAsArrayList.size() <= 0) {
            (Toast.makeText(getApplicationContext(), "Possible wrong Username Password Combination", Toast.LENGTH_LONG)).show();
        }
    }

    private void addClassButtons() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        int index = 0;
        for (Class i : DataStorageAndParsing.classesAsArrayList) {
            LinearLayout.LayoutParams bparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    350);
            LinearLayout.LayoutParams lnparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            linearLayout.setLayoutParams(lnparams);

            LinearLayout button = new LinearLayout(this);
            button.setLayoutParams(bparams);
            button.setWeightSum(3.0f);
            TextView name = new TextView(this);
            TextView grade = new TextView(this);
            TextView percentage = new TextView(this);
            name.setText(i.getName());
            grade.setText(i.getGrade());
            percentage.setText("" + i.getPercentage());
            name.setTextColor(Color.WHITE);
            grade.setTextColor(Color.WHITE);
            percentage.setTextColor(Color.WHITE);
            name.setBackgroundColor(i.getColor());
            grade.setBackgroundColor(i.getColor());
            percentage.setBackgroundColor(i.getColor());
            name.setTextSize(15);
            grade.setTextSize(20);
            percentage.setTextSize(20);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            name.setLayoutParams(param);
            grade.setLayoutParams(param);
            percentage.setLayoutParams(param);
            name.setGravity(Gravity.CENTER);
            grade.setGravity(Gravity.CENTER);
            percentage.setGravity(Gravity.CENTER);
            button.setBackgroundColor(i.getColor());
            button.addView(name);
            button.addView(grade);
            button.addView(percentage);

            button.setPadding(100, 10, 100, 10);
            final int finalIndex = index;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), ClassActivity.class);
                    intent.putExtra("class_index", finalIndex);
                    startActivity(intent);
                }
            });
            linearLayout.addView(button);
            Log.w(TAG, "INDEX: " + index);
            index++;
        }

        scrollView.addView(linearLayout);
        Log.v(TAG, "AddClassButtons Done");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        for (AsyncTask i : asyncTasks) {
            i.cancel(true);
        }
        asyncTasks.clear();
        DataStorageAndParsing.resetData();
        GetAssignment.resetData();
        GetData.resetData();
        PostData.resetData();
        LoginActivity.resetData();
    }

}
