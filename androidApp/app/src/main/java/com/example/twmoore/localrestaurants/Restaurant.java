package com.example.twmoore.localrestaurants;

/**
 * Created by lackeyw on 10/29/16.
 */

public class Restaurant {

    private int rating;

    private double distance;

    private String name, type;

    private boolean doesDeliv;

    public Restaurant(String name, double distance, boolean doesDeliv, int closeTime){

        this.rating = closeTime;

        this.name = name;
        this.distance = distance;
        this.doesDeliv = doesDeliv;
    }

    public void setRating(int close){

        rating = close;

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

    public int getRating() {
        return rating;
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

    public String ratingString(){
        return rating + "/5";
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
