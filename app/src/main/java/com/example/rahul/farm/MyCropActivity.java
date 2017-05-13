package com.example.rahul.farm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rahul.farm.Model.Crop;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCropActivity extends AppCompatActivity {

    ImageView add;
    List<String> list_crop;
    List<String> list_type;

    List<Crop> list_card;
    Crop crop;

    MyCropAdapter mCropAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_crop);

        mRecyclerView = (RecyclerView)findViewById(R.id.mycroplist);

        add = (ImageView)findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCropAlertDialog();

            }
        });

        list_card = new ArrayList<>();

        final ProgressDialog progress = ProgressDialog.show(MyCropActivity.this, "Please wait.", "Loading crop types...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, Config.GET_SAVED_CROP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        crop = new Crop();
                        JSONObject object = array.getJSONObject(i);
                        crop.cropName = object.getString("crop_name");
                        crop.sownDate = object.getString("sown_date");

                        list_card.add(crop);
                    }
                    mCropAdapter = new MyCropAdapter(list_card, MyCropActivity.this);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyCropActivity.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(MyCropActivity.this));
                    mRecyclerView.setAdapter(mCropAdapter);
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
                params.put("FARMER_ID", SharedPrefManager.getmInstance(MyCropActivity.this).getUserId());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(MyCropActivity.this);
        queue.add(request);
    }
    String cropName;

    private void AddCropAlertDialog() {

        final AlertDialog.Builder addDialog = new AlertDialog.Builder(MyCropActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_crop_dialog, null);
        addDialog.setView(dialogView);
        final TextView tvSownDate = (TextView)dialogView.findViewById(R.id.date_sown);
        final Button buttonSave = (Button) dialogView.findViewById(R.id.Add);

        tvSownDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //current date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(MyCropActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tvSownDate.setText(year+"/"+(month+1)+"/"+day);
                    }
                }, mYear, mMonth, mDay);
                datePicker.show();
            }
        });
        MaterialSpinner spinner_crop_type = (MaterialSpinner) dialogView.findViewById(R.id.crop_type);
        final MaterialSpinner spinner_crop_name = (MaterialSpinner) dialogView.findViewById(R.id.crop_name);

        spinner_crop_type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                LoadCrops(item, spinner_crop_name);
            }
        });

        spinner_crop_name.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                cropName = item;
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCrop(cropName, tvSownDate.getText().toString());
            }
        });

        final AlertDialog b = addDialog.create();

        LoadCropType(spinner_crop_type, b);
        LoadCrops("Kharif",spinner_crop_name);

        //b.show();
    }


    private void LoadCropType(final MaterialSpinner spinner_crop_type, final AlertDialog b) {
        final ProgressDialog progress = ProgressDialog.show(MyCropActivity.this, "Please wait.", "Loading crop types...", false, false);
        list_type = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, Config.ADD_CROP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray((response));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String crop = object.getString("crop");
                        list_type.add(crop);
                    }
                    spinner_crop_type.setItems(list_type);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                b.show();
                progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(MyCropActivity.this, "Oops! Something went off.", Toast.LENGTH_SHORT).show();
                b.show();
            }
        }){
        };
        RequestQueue queue = Volley.newRequestQueue(MyCropActivity.this);
        queue.add(request);
    }



    private void LoadCrops(final String type_crop, final MaterialSpinner spinner_crop_name) {
        final ProgressDialog progress = ProgressDialog.show(MyCropActivity.this, "Please wait.", "Loading crops...", false, false);
        list_crop = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, Config.CROP_TYPE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray((response));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String crop = object.getString("type");
                        list_crop.add(crop);
                    }
                    spinner_crop_name.setItems(list_crop);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", type_crop);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(MyCropActivity.this);
        queue.add(request);
    }


    private void saveCrop(final String cropName, final String sownDate) {

        final ProgressDialog progress = ProgressDialog.show(MyCropActivity.this, "Please wait.", "Saving crops...", false, false);
        list_crop = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, Config.SAVE_CROP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1"))
                    Toast.makeText(MyCropActivity.this, "crop saved!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MyCropActivity.this, "Ooops! Error occurred! "+response, Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("FARMER_ID", SharedPrefManager.getmInstance(MyCropActivity.this).getUserId());
                params.put("CROP_NAME", cropName);
                params.put("SOWN_DATE", sownDate);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(MyCropActivity.this);
        queue.add(request);

    }
}
