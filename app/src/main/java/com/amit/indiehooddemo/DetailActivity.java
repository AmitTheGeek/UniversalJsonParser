package com.amit.indiehooddemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amit.indiehooddemo.adapter.SchemaAdapter;
import com.amit.indiehooddemo.models.SchemaDataType;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private static final String SCHEMA_URL = "https://ui-test-dot-indihood-dev-in.appspot.com/schema";

    RequestQueue requestQueue;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        String djson = getIntent().getStringExtra("datajson");
        String sjson = getIntent().getStringExtra("schemajson");
        String name = getIntent().getStringExtra("name");


        requestQueue = Volley.newRequestQueue(this);
        recyclerView = findViewById(R.id.root_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        try {



            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            JSONObject dataJson = new JSONObject(djson);
            JSONObject schemaJson = new JSONObject(sjson);

            JSONObject data = dataJson.getJSONObject(name);


            JSONObject schema = schemaJson.getJSONObject(data.getString("type"));

            schema.remove("type");
            Type mapType = new TypeToken<Map<String, SchemaDataType>>() {}.getType();
            Map<String, SchemaDataType> map = gson.fromJson(schema.toString(), mapType);
            Log.d(TAG, "response map == " + map);




            adapter = new SchemaAdapter(schemaJson, data, map, this);
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadSchema(){
        //getting the progressbar
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, SCHEMA_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                       // loadData(response);
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


}
