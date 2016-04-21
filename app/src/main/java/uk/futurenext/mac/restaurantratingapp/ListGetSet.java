package uk.futurenext.mac.restaurantratingapp;

import com.google.android.gms.common.data.DataHolder;

import java.util.ArrayList;

/**
 * Created by Mac on 17/04/16.
 */
public final class ListGetSet {
    private  ArrayList<String> businessList = new ArrayList<>();

    private ArrayList<String> curLoc = new ArrayList<>();

    public void setStringList(ArrayList<String> businessList) {
        this.businessList = businessList;
    }

    public ArrayList<String> getStringList() {
        return businessList;
    }

    private String curLat;
    private String curLon;

    private String name;
    private String add1;
    private String add2;
    private String add3;
    private String post;
    private String rating_val;
    private String rating_date;
    private String distance;
    private String longitude;
    private String latitude;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRating_date() {
        return rating_date;
    }

    public void setRating_date(String rating_date) {
        this.rating_date = rating_date;
    }

    public String getRating_val() {
        return rating_val;
    }

    public void setRating_val(String rating_val) {
        this.rating_val = rating_val;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAdd3() {
        return add3;
    }

    public void setAdd3(String add3) {
        this.add3 = add3;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public void setVal(String name, String add1, String add2, String add3, String rating_val, String post){
        this.name = name;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.rating_val = rating_val;
        this.post = post;
    }
    public void setGpsVal (String name, String add1, String add2, String add3, String rating_val, String post, String latitude, String longitude, String distance){
        this.name = name;
        this.add1 = add1;
        this.add2 = add2;
        this.add3 = add3;
        this.rating_val = rating_val;
        this.post = post;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public String getCurLon() {
        return curLon;
    }

    public void setCurLon(String curLon) {
        this.curLon = curLon;
    }

    public String getCurLat() {
        return curLat;
    }

    public void setCurLat(String curLat) {
        this.curLat = curLat;
    }

    public ArrayList<String> getCurLoc() {
        return curLoc;
    }

    public void setCurLoc(ArrayList<String> curLoc) {
        this.curLoc = curLoc;
    }
}
