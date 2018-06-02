package com.androidtutorialpoint.newsreader;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohammadassad on 23/7/2017.
 */

public class Connection  {

    protected static final String TYPE_UTF8_CHARSET = "charset=UTF-8";
    //private static boolean classBoolean = false;


    public OnJSONObjectResponseListener mListener;






    public  void connectToServer(String url, final String SessionID, final Context cxt, int method){

        RequestQueue queue=null;
        if(cxt!=null)
        queue = Volley.newRequestQueue(cxt);


        JsonObjectRequest getRequest = new JsonObjectRequest(method, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {


                        //so you can change a class var
                        //classBoolean = true;
                        //or you can also call some method to do something
                        //setResponse(response);

                        if(mListener!=null){
                            mListener.onResponse(response,null);
                        }



                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            mListener.onResponse(null,message);
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                            mListener.onResponse(null,message);
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            mListener.onResponse(null,message);
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                            mListener.onResponse(null,message);
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            mListener.onResponse(null,message);
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                            mListener.onResponse(null,message);
                        }
                        else{
                            mListener.onResponse(null,volleyError.getMessage());
                        }
                       // DisplayMessage.displayMessage(message,cxt);
                    }

                }

        ) {


            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }



                try {
                    if(volleyError.getMessage() !=null) {
                        JSONObject jsonResponse = new JSONObject(volleyError.getMessage());
                        String errorMessage = jsonResponse.getString("errorMsg");


                       // DisplayMessage.displayMessage(errorMessage,cxt);

                        System.out.println("The full error is " + volleyError.getMessage());
                    }
                    else {
                        String message=null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            //mListener.onResponse(null,message);
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                            //mListener.onResponse(null,message);
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            //mListener.onResponse(null,message);
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                            //mListener.onResponse(null,message);
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                            //mListener.onResponse(null,message);
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                            //mListener.onResponse(null,message);
                        }

                       // DisplayMessage.displayMessage(message,cxt);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



                return volleyError;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                //uncomment this when dealing with real data;
                headers.put("Cookie", SessionID);
//headers.put("Cookie",Constants.session_id);

                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> responseHeaders = response.headers;


                System.out.println("\nLogging IN " + Arrays.asList(responseHeaders)); // method 1
                return super.parseNetworkResponse(response);
            }

            ;
        };


        //getRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 1, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequest.setShouldCache(false);
        queue.add(getRequest);




    }










   public  void setCustomEventListener(OnJSONObjectResponseListener eventListener) {
        this.mListener=eventListener;
    }


}
