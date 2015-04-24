package com.matchrace.matchrace.classes;

/**
 * Created by vladlanda on 24/04/15.
 */
public class BuoyPosition {
    String lat,lng;
    public BuoyPosition(String _lat, String _lng){
        lat = _lat;
        lng = _lng;
    }
    public String getLat(){return lat;}
    public String getLng(){return lng;}
}
