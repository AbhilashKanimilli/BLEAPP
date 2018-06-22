package com.example.user.bleapp;

/**
 * Created by user on 13/4/2018.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Result {
    @SerializedName("Sensordata")
    @Expose
    private List<Sensordata> sensordata = new ArrayList<Sensordata>();
    /**
     *
     * @return The todos
     */
    public List<Sensordata> getSensordata() {
        return sensordata;
    }
    /**
     *
     * @param sensordata
     * The todos
     */
    public void setSensordata(List<Sensordata> sensordata) {
        this.sensordata = sensordata;
    }
}
