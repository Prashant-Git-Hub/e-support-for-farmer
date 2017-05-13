package com.example.rahul.farm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {


    EditText etName;
    EditText etMobile;
    EditText etPassword;
    boolean invalid;
    //TextView tv_location;
    Button btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = (EditText)findViewById(R.id.name);
        etMobile = (EditText)findViewById(R.id.mobile);
        etPassword = (EditText)findViewById(R.id.password);
        btSubmit = (Button)findViewById(R.id.create);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendUserInfo(
                        etName.getText().toString(),
                        etMobile.getText().toString(),
                        etPassword.getText().toString());
            }
        });


    }

    private void sendUserInfo(final String name, final String mobile, final String password){

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_CREATE_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("mobile", mobile);
                params.put("password", password);
                if (name.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(),
                            "Please enter your Name", Toast.LENGTH_SHORT)
                            .show();
                }
                else
                if (mobile.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(),
                            "Please enter your Mobile no.", Toast.LENGTH_SHORT)
                            .show();
                }
                else
                if (password.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(),
                            "Please enter your Password", Toast.LENGTH_SHORT)
                            .show();
                }
                if(invalid == true)
                    startActivity(new Intent(SignupActivity.this, SignupActivity.class));

                    return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}
