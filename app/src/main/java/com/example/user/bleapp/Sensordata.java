package com.example.user.bleapp;

/**
 * Created by user on 13/4/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Sensordata {
    @SerializedName("device_Address")
    @Expose
    private String device_Address;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("time")
    @Expose
    private String time;
    /**
     *
     * @return The id
     */
    public String getDevice_Address() {
        return device_Address;
    }

    public void setDevice_Address(String device_Address) {
        this.device_Address = device_Address;
    }
    /**
     *
     * @return The title
     */
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    /**
     *
     * @return The completed
     */
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
