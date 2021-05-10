package com.example.jokeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // when you run some heavy tasks, you should run it in another thread, not main thread,
    // otherwise it might crash.
    // Here we add the JsonObjectRequest to the queue so that it will get run.
    // RequestQueue mRequestQueue;
    // RequestQueue mRequestQueueArray;
    TextView setupTv;
    TextView punchlineTv;
    Button getJokeBtn;
    RequestQueue mSingleRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRequestQueue = Volley.newRequestQueue(this);
//        mRequestQueueArray = Volley.newRequestQueue(this);
        setupTv = findViewById(R.id.setup);
        punchlineTv = findViewById(R.id.punchline);
        getJokeBtn = findViewById(R.id.getJokeBtn);
        mSingleRequestQueue = VolleySingleton.getInstance().getRequestQueue();



        getJokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        "https://official-joke-api.appspot.com/random_joke", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Response Success", response + "");

                                try {
                                    String setup = response.getString("setup");
                                    String punchLine = response.getString("punchline");
                                    setupTv.setText(setup);
                                    punchlineTv.setText(punchLine);

                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "Your Internet connection is gone", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response Error", error.getMessage());
                    }
                });

                mSingleRequestQueue.add(jsonObjectRequest);
            }
        });

//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
//                "https://official-joke-api.appspot.com/random_ten", null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.i("Array Response Success", response + "");
//
//                        for (int index = 0; index < response.length(); index++) {
//                            try {
//                                JSONObject jokeJsonObject = response.getJSONObject(index);
//                                Log.i("JOKE", jokeJsonObject.getString("setup") +
//                                        " " + jokeJsonObject.getString("punchline"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Array Response Error", error.getMessage());
//                    }
//                });
//
//        mSingleRequestQueue.add(jsonArrayRequest);
    }
}