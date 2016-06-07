package com.vasanth.dev.aeriesorhs;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import java.util.ArrayList;
/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class MainActivity extends AppCompatActivity {
    public static String classes="";
    public static String assignments ="";
    public static JSONObject jsonObj = null;
    public static ArrayList<Class> Classes = new ArrayList<Class>();
    public final String TAG = "MainActivity";
    public static boolean doneLoadingClasses = false;
    ArrayList<Integer> colors = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        colors.add(Color.parseColor("#3C3970"));
        colors.add(Color.parseColor("#5FBF75"));
        colors.add(Color.parseColor("#83CFD1"));
        colors.add(Color.parseColor("#BA85E2"));
        colors.add(Color.parseColor("#784DC1"));

        PostData postData = new PostData();
        postData.execute(new String[]{"ayy"});
        GetData getData = new GetData();
        getData.execute(new String[]{"ayy"});
        while(classes == ""){
        }
        Log.v(TAG, "Classes Loaded");



        //////////// USING PREVIOUS JSON /////////
        /*try {
            InputStream input = assetManager.open("json");
            int size = input.available();
            byte[] buffer=new byte[size];
            input.read(buffer);
            input.close();
            classes = new String(buffer);

        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }*/
        /////////////////////////////////////////


        ////////// GETTING THE ASSIGNMENTS//////////

        /*try {
            InputStream input = assetManager.open("assignments.html");
            int size = input.available();
            byte[] buffer=new byte[size];
            input.read(buffer);
            input.close();
            assignments = new String(buffer);

        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }*/
        ////////////////////////////////////////////



        getAssignment getAssignments = new getAssignment();
        getAssignments.execute(new String[]{});
        Log.v(TAG, "getASsignment.done: "+getAssignment.done);
        addClasses();
        addClassButtons();
        if(Classes.size()<=0){
            (Toast.makeText(getApplicationContext(),"Possible wrong Username Password Combination", Toast.LENGTH_LONG)).show();
        }
    }
    private void addClassButtons() {

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        int index=0;
        for(Class i : Classes){
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
            //button.setText( + StringOps.repeat(" ", (30 - i.getName().length())) + i.getGrade() + StringOps.repeat(" ", (40 - (i.getName().length() + Float.toString(i.getPercentage()).length() + (30 - i.getName().length())))) + i.getPercentage());
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
            Log.w(TAG, "INDEX: "+index);
            index++;
        }

        scrollView.addView(linearLayout);



// Add the LinearLayout element to the ScrollView
        Log.v(TAG, "AddClassButtons Done");
    }
    public void addClasses(){
        try {
            jsonObj = new JSONObject(classes);
            JSONArray data = jsonObj.getJSONArray("Data");

            //check if inside of data, classes are induvidual
            if (data.length() > 1) {
                for (int i = 0; i < data.length(); i++) {
                    String coursename;
                    String currentmark;
                    Float percentage;
                    String dateUpdated;
                    String assignmentLink;
                    try {
                        //coursename=data.getJSONObject(i).getString("CourseName");
                        coursename=(Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text();
                        Log.v(TAG, "Coursename: "+coursename);
                        //if(coursename.equalsIgnoreCase("Null") || coursename==null )
                            //coursename=(Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text();
                    }catch (Exception e){
                        Log.v(TAG, "Coursename: " + (Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text());
                        coursename = "Null";
                    }
                    try {
                        currentmark = data.getJSONObject(i).getString("CurrentMark");
                    }catch (Exception e){
                        currentmark = "Null";
                    }
                    try {
                        percentage = Float.parseFloat(data.getJSONObject(i).getString("Percent"));
                    }catch (Exception e){
                        percentage = new Float(0);
                    }
                    try {
                        dateUpdated = data.getJSONObject(i).getString("LastUpdated");
                    }catch (Exception e){
                        dateUpdated = "";
                    }
                    try {
                        assignmentLink = data.getJSONObject(i).getString("Gradebook");
                    }catch (Exception e){
                        assignmentLink = "";
                }
                    Classes.add(new Class(coursename,currentmark,percentage,colors.get(i % colors.size()),dateUpdated,assignmentLink));
                }
            }

            //if conjoined...
            else{
                Log.v(TAG, "Conjoined Classes");
                jsonObj = data.getJSONObject(0);
                data = jsonObj.getJSONArray("Items");
                for (int i = 0; i < data.length(); i++) {
                    String coursename;
                    String currentmark;
                    Float percentage;
                    String dateUpdated;
                    String assignmentLink;
                    try {
                        //coursename=data.getJSONObject(i).getString("CourseName");
                        coursename=(Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text();
                        Log.v(TAG, "Coursename: "+coursename);
                        //if(coursename.equalsIgnoreCase("Null") || coursename==null )
                            //coursename=(Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text();
                    }catch (Exception e){
                        //Log.v(TAG, "Coursename: " + (Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text());
                        coursename = "Null";

                    }
                    try {
                        currentmark = data.getJSONObject(i).getString("CurrentMark");
                    }catch (Exception e){
                        currentmark = "Null";
                    }
                    try {
                        percentage = Float.parseFloat(data.getJSONObject(i).getString("Percent"));
                    }catch (Exception e){
                        percentage = new Float(0);
                    }
                    try {
                        dateUpdated = data.getJSONObject(i).getString("LastUpdated");
                    }catch (Exception e){
                        dateUpdated = "";
                    }
                    try {
                        assignmentLink = data.getJSONObject(i).getString("Gradebook");
                    }catch (Exception e){
                        assignmentLink = "";
                    }
                    Classes.add(new Class(coursename,currentmark,percentage,colors.get(i % colors.size()),dateUpdated,assignmentLink));
                }
                for( Class p : MainActivity.Classes){
                    Log.v(TAG, "Classes before revision" +p.getName());
                }
                //sort and remove duplicates
                ArrayList<Class> tempClasses = new ArrayList<Class>();
                ArrayList<Integer> blackListIndex = new ArrayList<Integer>();

                for(int i=0; i<Classes.size(); i++){
                    Class mainClass = Classes.get(i);
                    boolean arethereduplicates=false;
                    boolean sames=false;
                    for (int j=0; j<Classes.size(); j++){
                        try {
                            Log.v(TAG, "index i: " + i);
                            Log.v(TAG, "index j: " + j);
                            Class classToCheck = Classes.get(j);
                            if ((classToCheck.getName().equals(mainClass.getName())) && (i != j) && (!blackListIndex.contains(i) || !blackListIndex.contains(j))) {
                                Log.v(TAG, "Classes Size 1: " + Classes.size());
                                Log.v(TAG, "Names: " + classToCheck.getName() + " " + mainClass.getName());
                                Class correctClass = Class.CompareDuplicates(mainClass, classToCheck);
                                tempClasses.add(correctClass);
                                blackListIndex.add(j);
                                blackListIndex.add(i);
                                //reinitialize loop to go to beginning
                                //i = 0;
                                //j = 0;
                                Log.v(TAG, "Classes Size 2: " + tempClasses.size());
                                arethereduplicates = true;

                            }
                            if(i==j){
                                Log.v(TAG, "There Are sames");
                                sames=true;
                            }

                        }catch(Exception e){
                            Log.e(TAG, "Error: "+e.toString());
                            Log.e(TAG, "Classes Size E: " + tempClasses.size());
                        }

                    }
                    Log.v(TAG, "Classes Size F: " + tempClasses.size());

                    if(!arethereduplicates && sames) {
                        tempClasses.add(mainClass);
                        //Log.v(TAG, "No Duplicates found");
                    }

                }
                Classes = tempClasses;
            }
        }
        catch (Exception e){
            Log.e("MainActivity", "Error: "+e.toString());
        }
        Log.v(TAG, "AddClasses Done");
        doneLoadingClasses = true;
    }

}
