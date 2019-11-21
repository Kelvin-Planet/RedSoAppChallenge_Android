package com.kelvinlee.appchallenge_java.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CatalogModel {
    public String id = "";
    public String type = "";

    public static ArrayList<CatalogModel> parse(JSONObject obj) {
        ArrayList<CatalogModel> list = new ArrayList<CatalogModel>();
        try {
            JSONArray arr = obj.getJSONArray("results");
            for (int i = 0 ; i < arr.length(); i++) {
                JSONObject one = arr.getJSONObject(i);
                if (one.getString("type").toString().equals("banner")) {
                    BannerModel m = new BannerModel(one);
                    list.add(m);
                }
                if (one.getString("type").toString().equals("employee")) {
                    EmployeeModel m = new EmployeeModel(one);
                    list.add(m);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
