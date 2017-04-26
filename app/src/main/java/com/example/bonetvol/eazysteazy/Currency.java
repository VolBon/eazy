package com.example.bonetvol.eazysteazy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public abstract class Currency extends Context {
    Double currencyRate;

    public Double click(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://apilayer.net/api/live?access_key=a158456551a829bcfa8fa137341e071e&currencies=UAH&format=1";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,205));
                        Log.i("Response", response);
                        Double jsoni = Double.valueOf(response.substring(190, 195));// + Integer.valueOf(response.substring(193, 195))/100;
                        Log.i("HERE", String.valueOf(jsoni));
                        currencyRate = jsoni;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("That didn't work!", "no");
            }
        });
        queue.add(stringRequest);
        return currencyRate;
    }

}
