package com.vasanth.dev.aeriesorhs.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vasanth.dev.aeriesorhs.R;
import com.vasanth.dev.aeriesorhs.adapters.AdapterAssignment;
import com.vasanth.dev.aeriesorhs.adapters.AdapterClass;
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
        addAssignmentsNew();
    }

    public void setColor() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_assignment_layout);
        linearLayout.setBackgroundColor(classMain.getColor());
    }
    public void addAssignmentsNew() {
        ListView mainListView = (ListView) findViewById( R.id.assignmentListView );
        AdapterAssignment listAdapter = new AdapterAssignment(this, classMain.getAssignments(), classMain.getColor());
        mainListView.setAdapter( listAdapter );
    }
}
