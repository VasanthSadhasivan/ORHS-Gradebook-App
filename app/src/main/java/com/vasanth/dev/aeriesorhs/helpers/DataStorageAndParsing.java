package com.vasanth.dev.aeriesorhs.helpers;

import android.graphics.Color;
import android.util.Log;

import com.vasanth.dev.aeriesorhs.objects.Class;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;

/**
 * Created by Admin on 11/26/2016.
 */

public class DataStorageAndParsing {
    public static String TAG = "DataStorageAndParsing";
    public static String classesAsString;
    public static ArrayList<Class> classesAsArrayList = new ArrayList<Class>();
    public static JSONObject classesAsJsonObject;
    public static ArrayList<Integer> colors = new ArrayList<Integer>();
    public static boolean doneLoadingClasses = false;

    public static void setColors() {
        colors.add(Color.parseColor("#3C3970"));
        colors.add(Color.parseColor("#5FBF75"));
        colors.add(Color.parseColor("#83CFD1"));
        colors.add(Color.parseColor("#BA85E2"));
        colors.add(Color.parseColor("#784DC1"));
    }

    public static void createClasses() {
        try {
            classesAsJsonObject = new JSONObject(classesAsString);
            JSONArray data = classesAsJsonObject.getJSONArray("Data");

            if (data.length() == 2) {
                for (int i = 0; i < data.getJSONObject(0).getJSONArray("Items").length(); i++) {
                    String coursename;
                    String currentmark;
                    Float percentage;
                    String dateUpdated;
                    String assignmentLink;
                    try {
                        coursename = (Jsoup.parse(data.getJSONObject(0).getJSONArray("Items").getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text();
                        Log.v(TAG, "Coursename: " + coursename);
                        if (coursename.equals("null")) {
                            coursename = (Jsoup.parse(data.getJSONObject(0).getJSONArray("Items").getJSONObject(i).getString("CourseName"))).getAllElements().get(0).text();
                        }
                    } catch (Exception e) {
                        Log.v(TAG, "Coursename: " + (Jsoup.parse(data.getJSONObject(0).getJSONArray("Items").getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text());

                        try {
                            coursename = (Jsoup.parse(data.getJSONObject(0).getJSONArray("Items").getJSONObject(i).getString("CourseName"))).getAllElements().get(0).text();
                        } catch (Exception v) {
                            coursename = "Null";
                        }
                    }
                    try {
                        currentmark = data.getJSONObject(0).getJSONArray("Items").getJSONObject(i).getString("CurrentMark");
                    } catch (Exception e) {
                        currentmark = "Null";
                    }
                    try {
                        percentage = Float.parseFloat(data.getJSONObject(0).getJSONArray("Items").getJSONObject(i).getString("Percent"));
                    } catch (Exception e) {
                        percentage = new Float(0);
                    }
                    try {
                        dateUpdated = data.getJSONObject(0).getJSONArray("Items").getJSONObject(i).getString("LastUpdated");
                    } catch (Exception e) {
                        dateUpdated = "";
                    }
                    try {
                        assignmentLink = data.getJSONObject(0).getJSONArray("Items").getJSONObject(i).getString("Gradebook");
                    } catch (Exception e) {
                        assignmentLink = "";
                    }
                    classesAsArrayList.add(new Class(coursename, currentmark, percentage, colors.get(i % colors.size()), dateUpdated, assignmentLink));
                }
            } else if (data.length() > 1) {
                for (int i = 0; i < data.length(); i++) {
                    String coursename;
                    String currentmark;
                    Float percentage;
                    String dateUpdated;
                    String assignmentLink;
                    try {
                        coursename = (Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text();
                        Log.v(TAG, "Coursename: " + coursename);
                        if (coursename.equals("null")) {
                            coursename = (Jsoup.parse(data.getJSONObject(i).getString("CourseName"))).getAllElements().get(0).text();
                        }
                    } catch (Exception e) {
                        Log.v(TAG, "Coursename: " + (Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text());
                        try {
                            coursename = (Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text();
                        } catch (Exception v) {
                            coursename = "Null";
                        }
                    }
                    try {
                        currentmark = data.getJSONObject(i).getString("CurrentMark");
                    } catch (Exception e) {
                        currentmark = "Null";
                    }
                    try {
                        percentage = Float.parseFloat(data.getJSONObject(i).getString("Percent"));
                    } catch (Exception e) {
                        percentage = new Float(0);
                    }
                    try {
                        dateUpdated = data.getJSONObject(i).getString("LastUpdated");
                    } catch (Exception e) {
                        dateUpdated = "";
                    }
                    try {
                        assignmentLink = data.getJSONObject(i).getString("Gradebook");
                    } catch (Exception e) {
                        assignmentLink = "";
                    }
                    classesAsArrayList.add(new Class(coursename, currentmark, percentage, colors.get(i % colors.size()), dateUpdated, assignmentLink));
                }
            }
            else {
                Log.v(TAG, "Conjoined classesAsArrayList");
                classesAsJsonObject = data.getJSONObject(0);
                data = classesAsJsonObject.getJSONArray("Items");
                for (int i = 0; i < data.length(); i++) {
                    String coursename;
                    String currentmark;
                    Float percentage;
                    String dateUpdated;
                    String assignmentLink;
                    try {
                        coursename = (Jsoup.parse(data.getJSONObject(i).getString("Gradebook"))).getAllElements().get(0).text();
                        Log.v(TAG, "Coursename: " + coursename);
                        if (coursename.equals("null")) {
                            coursename = (Jsoup.parse(data.getJSONObject(i).getString("CourseName"))).getAllElements().get(0).text();
                        }
                    } catch (Exception e) {
                        try {
                            coursename = (Jsoup.parse(data.getJSONObject(i).getString("CourseName"))).getAllElements().get(0).text();
                        } catch (Exception v) {
                            coursename = "Null";
                        }

                    }
                    try {
                        currentmark = data.getJSONObject(i).getString("CurrentMark");
                    } catch (Exception e) {
                        currentmark = "Null";
                    }
                    try {
                        percentage = Float.parseFloat(data.getJSONObject(i).getString("Percent"));
                    } catch (Exception e) {
                        percentage = new Float(0);
                    }
                    try {
                        dateUpdated = data.getJSONObject(i).getString("LastUpdated");
                    } catch (Exception e) {
                        dateUpdated = "";
                    }
                    try {
                        assignmentLink = data.getJSONObject(i).getString("Gradebook");
                    } catch (Exception e) {
                        assignmentLink = "";
                    }
                    classesAsArrayList.add(new Class(coursename, currentmark, percentage, colors.get(i % colors.size()), dateUpdated, assignmentLink));
                }
                for (Class p : classesAsArrayList) {
                    Log.v(TAG, "Classes before revision" + p.getName());
                }
                ArrayList<Class> tempClasses = new ArrayList<Class>();
                ArrayList<Integer> blackListIndex = new ArrayList<Integer>();

                for (int i = 0; i < classesAsArrayList.size(); i++) {
                    Class mainClass = classesAsArrayList.get(i);
                    boolean arethereduplicates = false;
                    boolean sames = false;
                    for (int j = 0; j < classesAsArrayList.size(); j++) {
                        try {
                            Log.v(TAG, "index i: " + i);
                            Log.v(TAG, "index j: " + j);
                            Class classToCheck = classesAsArrayList.get(j);
                            if ((classToCheck.getName().equals(mainClass.getName())) && (i != j) && (!blackListIndex.contains(i) || !blackListIndex.contains(j))) {
                                Log.v(TAG, "Classes Size 1: " + classesAsArrayList.size());
                                Log.v(TAG, "Names: " + classToCheck.getName() + " " + mainClass.getName());
                                Class correctClass = Class.CompareDuplicates(mainClass, classToCheck);
                                tempClasses.add(correctClass);
                                blackListIndex.add(j);
                                blackListIndex.add(i);
                                Log.v(TAG, "Classes Size 2: " + tempClasses.size());
                                arethereduplicates = true;

                            }
                            if (i == j) {
                                Log.v(TAG, "There Are sames");
                                sames = true;
                            }

                        } catch (Exception e) {
                            Log.e(TAG, "Error: " + e.toString());
                            Log.e(TAG, "Classes Size E: " + tempClasses.size());
                        }

                    }
                    Log.v(TAG, "Classes Size F: " + tempClasses.size());

                    if (!arethereduplicates && sames) {
                        tempClasses.add(mainClass);
                    }

                }
                classesAsArrayList = tempClasses;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "AddClasses Done");
        doneLoadingClasses = true;
    }

    public static void resetData(){
        classesAsString="";
        classesAsArrayList.clear();
        classesAsJsonObject = null;
        colors.clear();
        doneLoadingClasses = false;
    }
}
