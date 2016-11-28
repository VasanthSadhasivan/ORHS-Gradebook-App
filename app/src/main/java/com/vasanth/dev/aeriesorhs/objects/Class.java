package com.vasanth.dev.aeriesorhs.objects;

import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Float.NaN;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class Class {
    private ArrayList<Assignment> assignments = new ArrayList<Assignment>();
    private String name = "";
    private String grade = "";
    private float percentage;
    private int color;
    private String dateUpdated;
    private String assignmentLink;
    private boolean weighted = false;
    private HashMap hashMap = new HashMap();
    public Class(){

    }
    public Class(String name, String grade, float percentage, int color, String dateUpdated, String assignmentLink){
        this.grade=grade;
        this.percentage=percentage;
        this.name=name;
        this.color = color;
        this.dateUpdated = dateUpdated;
        this.assignmentLink = assignmentLink;
    }
    public void addAssignment(String name, float whatYouGot, float total, float percentage, boolean isGraded){
        assignments.add(new Assignment(name,whatYouGot,total,percentage,isGraded));
    }
    public void addAssignment(String name, float whatYouGot, float total, float percentage, boolean isGraded, String category){
        assignments.add(new Assignment(name,whatYouGot,total,percentage,isGraded, category));
    }
    public float getCalculatedPercentage(){
        int tempTotal=0;
        int tempWhatYouGot=0;
        float tempPercentage=0;
        for(Assignment a : assignments){
            tempTotal+=a.getTotal();
        }
        for(Assignment a : assignments){
            tempWhatYouGot+=a.getWhatYouGot();
        }
        tempPercentage=((float)tempWhatYouGot)/((float)tempTotal);
        return tempPercentage;
    }
    public String getName(){
        return name;
    }
    public String getGrade(){
        return grade;
    }
    public float getPercentage(){
        return percentage;
    }
    public int getColor(){
        return color;
    }
    public ArrayList<Assignment> getAssignments(){
        return assignments;
    }
    public String getDateUpdated(){
        return dateUpdated;
    }
    public String getAssignmentLink(){
        return this.assignmentLink;
    }
    public static Class CompareDuplicates(Class a, Class b){
        ArrayList<String> months = new ArrayList<String>();

        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec" );
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        for (String i : months){
            if (a.getDateUpdated().split("\\s+")[0].contains(i))
                return b;
            if (b.getDateUpdated().split("\\s+")[0].contains(i))
                return a;
        }
        return  a;
    }

    public boolean isWeighted() {
        return weighted;
    }

    public void setWeighted(boolean weighted) {
        this.weighted = weighted;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;
    }

    public float generateCalculatedGrade(){
        if(weighted) {
            float sumOfGrades = 0;
            for (Object key : hashMap.keySet()) {
                float sumOfCategory = 0;
                float totalInCategory = 0;
                for (Assignment assignment : assignments) {
                    if (assignment.isCounted() && assignment.getCategory().equalsIgnoreCase((String) key)) {
                        sumOfCategory += assignment.getWhatYouGot();
                        totalInCategory += assignment.getTotal();
                    }
                }
                Log.v("Class", (sumOfCategory / totalInCategory * 100) + " for key " + key);
                if (totalInCategory > 1)
                    sumOfGrades += (sumOfCategory / totalInCategory * 100) * (Float) hashMap.get(key) / 100f;

                Log.v("Class", sumOfGrades + ": Sum of Grades");
            }
            for (Object key : hashMap.keySet()) {
                float totalInCategory = 0;
                for (Assignment assignment : assignments) {
                    if (assignment.isCounted() && assignment.getCategory().equalsIgnoreCase((String) key))
                        totalInCategory += assignment.getTotal();
                }
                if (totalInCategory < 1)
                    sumOfGrades = sumOfGrades / (1 - (Float) hashMap.get(key) / 100);
            }
            return BigDecimal.valueOf(sumOfGrades).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
        float total=0;
        float whatIGot=0;
        for(Assignment assignment : assignments){
            total += assignment.getTotal();
            whatIGot += assignment.getWhatYouGot();
        }

        return BigDecimal.valueOf(whatIGot/total*100).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public ArrayList<String> getCategories(){
        ArrayList<String> categories = new ArrayList<String>();
        if(weighted) {
            for (Object key : hashMap.keySet()) {
                categories.add((String)key);
            }
            return categories;
        }
        return categories;
    }
}
