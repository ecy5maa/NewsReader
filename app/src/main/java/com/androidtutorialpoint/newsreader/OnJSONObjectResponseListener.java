package com.androidtutorialpoint.newsreader;



import org.json.JSONObject;

/**
 * Created by mohammadassad on 23/7/2017.
 */

public interface OnJSONObjectResponseListener {

     void onResponse(JSONObject response, String errorMessage);
}
