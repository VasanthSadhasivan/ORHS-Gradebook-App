package com.vasanth.dev.aeriesorhs;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class Assignment {
    private float total;
    private String name;
    private float whatYouGot;
    private float percentage;
    public Assignment(){

    }
    public Assignment(String name, float whatYouGot, float total, float percentage){
        this.name=name;
        this.whatYouGot=whatYouGot;
        this.total=total;
        this.percentage=percentage;
    }
    public void setTotal(float a){
        total = a;
    }
    public void setWhatYouGot(float a){
        whatYouGot = a;
    }
    public void setPercentage(float a){
        percentage = a;
    }
    public void setName(String a){
        name = a;
    }
    public float getTotal(){
        return total;
    }
    public float getWhatYouGot(){
        return whatYouGot;
    }
    public String getName(){
        return name;
    }
    public float getPercentage(){
        return percentage;
    }

}
