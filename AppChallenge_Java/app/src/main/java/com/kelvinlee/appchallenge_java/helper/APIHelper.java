package com.kelvinlee.appchallenge_java.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kelvinlee.appchallenge_java.model.CatalogModel;

import org.json.JSONObject;

import java.util.ArrayList;

public class APIHelper {

    public interface CompletionListener {
        public void onCompletion(ArrayList<CatalogModel> list);
    }

    static String SERVER_PATH = "https://us-central1-redso-challenge.cloudfunctions.net/";
    public static Context context;
    public static void loadCatalog(String team, int page, final CompletionListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, SERVER_PATH+"catalog?team="+ team + "&page=" + page, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("test",response.toString());
                        listener.onCompletion(CatalogModel.parse(response));
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("test",error.toString());
                    }
                });
        queue.add(jsonObjectRequest);
    }
}


