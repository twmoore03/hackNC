package com.example.twmoore.localrestaurants;

/**
 * Created by lackeyw on 10/29/16.
 */

public class Restaurant {

    private int closeTime;

    private double distance;

    private String name, type;

    private boolean doesDeliv;

    public Restaurant(String name, String type, int openTime, int closeTime){

        this.closeTime = closeTime;

        this.name = name;
        this.type = type;
        distance = 0;
        doesDeliv = false;
    }

    public void setCloseTime(int close){

        closeTime = close;

    }

    public void setName(String name){

        this.name = name;

    }

    public void setDistance(double dis){

        distance = dis;

    }

    public void setDoesDeliv(boolean doesDeliv) {
        this.doesDeliv = doesDeliv;
    }

    public boolean isDoesDeliv() {
        return doesDeliv;
    }

    public double getDistance() {
        return distance;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String distanceString(){
        return distance + " miles away";
    }

    public String closeString(){
        return closeTime + ":00";
    }

    public String delivString(){
        if(doesDeliv){
            return "Delivers";
        }

        else{
            return "Does not Deliver";
        }
    }
}
