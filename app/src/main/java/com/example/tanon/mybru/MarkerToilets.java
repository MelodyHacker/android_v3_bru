package com.example.tanon.mybru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by MelodyHacker on 10/30/2017.
 */

public class MarkerToilets extends AppCompatActivity {
    Url url = new Url();
    ProgressDialog mProgressDialog;
    String[] ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        mProgressDialog = new ProgressDialog(MarkerToilets.this);
        mProgressDialog.setMessage(getString(R.string.load));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
        load();
    }

    private void load() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.jsontoilte,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("toilets");
                            int x = 0;
                            ar = new String[jsonArray.length() * 3];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject MarkObject = jsonArray.getJSONObject(i);
                                ar[x] = MarkObject.getString("toilet_name");
                                ar[x + 1] = MarkObject.getString("toilet_lat");
                                ar[x + 2] = MarkObject.getString("toilet_long");
                                x = x + 3;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mProgressDialog.dismiss();
                        ///////////////////////////////////////////////////////////////
                        Intent intent = new Intent(MarkerToilets.this, MapsToilet.class);
                        intent.putExtra("arrayMarkerToilet", ar);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), getString(R.string.wrong)+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getApplication());
        requestQueue.add(stringRequest);

    }

}


