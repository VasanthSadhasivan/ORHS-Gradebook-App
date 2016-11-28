package com.vasanth.dev.aeriesorhs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vasanth.dev.aeriesorhs.R;
import com.vasanth.dev.aeriesorhs.adapters.AdapterAssignment;
import com.vasanth.dev.aeriesorhs.helpers.DataStorageAndParsing;
import com.vasanth.dev.aeriesorhs.objects.Class;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class ClassActivity extends AppCompatActivity {
    public Class classMain;
    public final String TAG = "ClassActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Intent intent = getIntent();
        int class_index = intent.getIntExtra("class_index", -1);
        classMain = DataStorageAndParsing.classesAsArrayList.get(class_index);
        changeGrade(classMain.generateCalculatedGrade());
        ((TextView) findViewById(R.id.ClassTitle)).setText(DataStorageAndParsing.classesAsArrayList.get(class_index).getName());
        setColor();
        addAssignmentsNew();
    }

    public void setColor() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_assignment_layout);
        linearLayout.setBackgroundColor(classMain.getColor());
    }

    public void addAssignmentsNew() {
        ListView mainListView = (ListView) findViewById(R.id.assignmentListView);
        AdapterAssignment listAdapter = new AdapterAssignment(this, classMain.getColor());
        mainListView.setAdapter(listAdapter);
    }

    public void changeGrade(float newGrade) {
        ((TextView) findViewById(R.id.ClassGrade)).setText(newGrade + "%");
    }
}
