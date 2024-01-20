package io.freddi.mineskin.data;

import com.google.gson.annotations.SerializedName;

public class SkinInfo {

    public int id;
    public String idStr;
    public String uuid;
    public String name;
    public String variant;
    public SkinData data;
    public long timestamp;
    public long duration;
    public int account;
    public String server;
    @SerializedName("private")
    public boolean prvate;
    public long views;

}
