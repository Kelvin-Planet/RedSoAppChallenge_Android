package com.kelvinlee.appchallenge_java.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeModel extends CatalogModel {
    public String name = "";
    public String position = "";
    public String expertise = "";
    public String avatar = "";
    public EmployeeModel(JSONObject obj) {
        try {
            id = obj.getString("id").toString();
            type = obj.getString("type").toString();
            name = obj.getString("name").toString();
            position = obj.getString("position").toString();
            expertise = obj.getJSONArray("expertise").join(", ").toString();
            avatar = obj.getString("avatar").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
