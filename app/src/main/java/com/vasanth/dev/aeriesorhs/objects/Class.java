package com.vasanth.dev.aeriesorhs.objects;

import java.util.ArrayList;

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
    public String getCalculatedGrade(float tempPercentage) {
        if (tempPercentage<= 10000 && tempPercentage>= 97) {
            return("A+");
        }
        else if (tempPercentage<= 96 && tempPercentage>= 94) {
            return("A");
        }
        else if (tempPercentage<= 93 && tempPercentage>= 90) {
            return("A-");
        }
        else if (tempPercentage<= 89 && tempPercentage>= 87) {
            return("B+");
        }
        else if (tempPercentage<= 86 && tempPercentage>= 83) {
            return("B");
        }
        else if (tempPercentage<= 82 && tempPercentage>= 80) {
            return("B-");
        }
        else if (tempPercentage<= 79 && tempPercentage>= 77) {
            return("C+");
        }
        else if (tempPercentage<= 76 && tempPercentage>= 73) {
            return("C");
        }
        else if (tempPercentage<= 72 && tempPercentage>= 70) {
            return("C-");
        }
        else if (tempPercentage<= 69 && tempPercentage>= 67) {
            return("D+");
        }
        else if (tempPercentage<= 66 && tempPercentage>= 63) {
            return("D");
        }
        else if (tempPercentage<= 62 && tempPercentage>= 60) {
            return("D-");
        }
        else{
            return("F");
        }
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
}
