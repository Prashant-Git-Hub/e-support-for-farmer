package com.example.rahul.farm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {

    EditText etMobile;
    EditText etPassword;
    Button btSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        etMobile = (EditText) findViewById(R.id.mobile);
        etPassword = (EditText) findViewById(R.id.password);
        btSignin = (Button) findViewById(R.id.signin);

        btSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateUser(
                        etMobile.getText().toString(),
                        etPassword.getText().toString());
            }
        });

    }

    private void validateUser(final String mobile, final String password) {

        final ProgressDialog progress = ProgressDialog.show(SigninActivity.this, "Please wait.", "Loading crop types...", false, false);

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_VALIDATE_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.equals("0")) {
                    try {

                        JSONArray oja = new JSONArray(response);
                        JSONObject ojo = oja.getJSONObject(0);
                        String firstName = ojo.getString("farmer_firstname");
                        String id = ojo.getString("farmer_id");
                        SharedPrefManager.getmInstance(SigninActivity.this).saveUserInfo("1", firstName, id);
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    catch (JSONException je) {
                        je.printStackTrace();
                    }


                }
                else
                    Toast.makeText(SigninActivity.this, "Phone number or Password is not correct", Toast.LENGTH_SHORT).show();

                    progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SigninActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);



    }
}
