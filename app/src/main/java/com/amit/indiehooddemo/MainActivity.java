package com.amit.indiehooddemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amit.indiehooddemo.adapter.ListAdapter;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String DATA_URL = "https://ui-test-dot-indihood-dev-in.appspot.com/records";

    private static final String SCHEMA_URL = "https://ui-test-dot-indihood-dev-in.appspot.com/schema";

    RequestQueue requestQueue;
    private ListAdapter adapter;
    private ListView listView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);

        requestQueue = Volley.newRequestQueue(this);
        listView = findViewById(R.id.root_list_view);

        loadSchema();
    }

    private void loadSchema(){
        //getting the progressbar

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, SCHEMA_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        loadData(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue

        //adding the string request to request queue
        requestQueue.add(jsonObjectRequest);
    }

    private void loadData(final JSONObject schemaJson){
        //getting the progressbar
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, DATA_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        processData(schemaJson, response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue

        //adding the string request to request queue
        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("NewApi")
    private void processData(final JSONObject schemaJson, final JSONObject dataJson){
        try {
            final ArrayList<String> dataList = new ArrayList<>();
            dataJson.keys().forEachRemaining(new Consumer<String>() {
                @Override
                public void accept(String key) {
                    Object keyvalue = null;
                    try {
                        keyvalue = dataJson.get(key);
                        dataList.add(key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("key: " + key + " value: " + keyvalue);
                }
            });
            progressBar.setVisibility(View.INVISIBLE);
            ListAdapter listAdapter = new ListAdapter(this, dataList, schemaJson, dataJson);
            listView.setAdapter(listAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
