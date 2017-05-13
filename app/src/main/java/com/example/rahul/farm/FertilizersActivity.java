package com.example.rahul.farm;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rahul.farm.Model.Crop;
import com.example.rahul.farm.Model.Fertilizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FertilizersActivity extends AppCompatActivity {

    List<Fertilizer> list_card;
    RecyclerView mRecyclerView;
    Fertilizer fert;
    FertilizersAdapter FAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizers);

        mRecyclerView = (RecyclerView)findViewById(R.id.fertilizerlist);

        list_card = new ArrayList<>();
        final ProgressDialog progress = ProgressDialog.show(FertilizersActivity.this, "Please wait.", "Loading crop types...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, Config.GET_FERTILIZERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        fert = new Fertilizer();
                        JSONObject object = array.getJSONObject(i);
                        fert.type = object.getString("type");
                        fert.Nitrogenous = object.getString("Nitrogenous");
                        fert.Phosphatic = object.getString("Phosphatic");
                        fert.Potassic = object.getString("Potassic");
                        fert.Manure = object.getString("Manure");

                        list_card.add(fert);
                    }
                    FAdapter = new FertilizersAdapter(list_card, FertilizersActivity.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(FertilizersActivity.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(FertilizersActivity.this));
                    mRecyclerView.setAdapter(FAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progress.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("FARMER_ID", SharedPrefManager.getmInstance(FertilizersActivity.this).getUserId());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(FertilizersActivity.this);
        queue.add(request);
    }
}

