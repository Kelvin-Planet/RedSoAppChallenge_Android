package com.kelvinlee.appchallenge_java.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class BannerModel extends CatalogModel {
    public String url = "";
    public BannerModel(JSONObject obj) {
        try {
            type = obj.getString("type").toString();
            url = obj.getString("url").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
