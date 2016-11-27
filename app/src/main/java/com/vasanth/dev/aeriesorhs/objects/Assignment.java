package com.vasanth.dev.aeriesorhs.objects;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class Assignment {
    private float total;
    private String name;
    private float whatYouGot;
    private float percentage;
    private boolean counted = true;
    public Assignment(){

    }
    public Assignment(String name, float whatYouGot, float total, float percentage, boolean counted){
        this.name=name;
        this.whatYouGot=whatYouGot;
        this.total=total;
        this.percentage=percentage;
        this.counted=counted;
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

    public boolean isCounted() {
        return counted;
    }

    public void setCounted(boolean counted) {
        this.counted = counted;
    }
}
