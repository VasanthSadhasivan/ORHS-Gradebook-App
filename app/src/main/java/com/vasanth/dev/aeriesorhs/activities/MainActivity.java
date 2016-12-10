package com.vasanth.dev.aeriesorhs.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.vasanth.dev.aeriesorhs.R;
import com.vasanth.dev.aeriesorhs.adapters.AdapterClass;
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
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!getIntent().getBooleanExtra("firstTime",false)){
            addClassButtonsNew();
            return;
        }

        DataStorageAndParsing.setColors();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Loading Assignments…");
        progressDialog.setCancelable(false);
        progressDialog.show();

        asyncTasks.add(new PostData());
        asyncTasks.add(new GetData());
        asyncTasks.add(new GetAssignment(this.getApplicationContext()));
        asyncTasks.get(0).execute(new String[]{"ayy"});
        asyncTasks.get(1).execute(new String[]{"ayy"});
        Log.v(TAG, "Classes Loading: " + DataStorageAndParsing.classesAsString);
        while (!GetData.finished) {
        }
        Log.v(TAG, "Classes Loaded, size: "+DataStorageAndParsing.classesAsArrayList.size());
        asyncTasks.get(2).execute(new String[]{});
        Log.v(TAG, "getASsignment.done: " + GetAssignment.done);
        DataStorageAndParsing.createClasses();
        addClassButtonsNew();
        if (DataStorageAndParsing.classesAsArrayList.size() <= 0) {
            (Toast.makeText(getApplicationContext(), "Possible wrong Username Password Combination", Toast.LENGTH_LONG)).show();
        }
    }

    private void addClassButtonsNew() {
        ListView mainListView = (ListView) findViewById( R.id.mainListView );

        AdapterClass listAdapter = new AdapterClass(this, DataStorageAndParsing.classesAsArrayList);
        mainListView.setAdapter( listAdapter );
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
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
    }
}