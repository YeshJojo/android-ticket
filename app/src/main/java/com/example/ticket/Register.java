package com.example.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText etName, etPass, etMail;
    ProgressBar load;
    String name, password, email;
    Button btRegister;
    private static String URL_REGIST = "http://bcb4937.000webhostapp.com/ticket/android_register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.name);
        etMail = findViewById(R.id.email);
        etPass = findViewById(R.id.pass);
        btRegister = findViewById(R.id.reg_btn);
        load = findViewById(R.id.loading);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();
            }
        });
    }

    private void Regist(){
        load.setVisibility(View.VISIBLE);
        btRegister.setVisibility(View.GONE);
        name = this.etName.getText().toString().trim();
        email = this.etMail.getText().toString().trim();
        password = this.etPass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(Register.this,"Register Success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this,LoginActivity.class));
                                load.setVisibility(View.GONE);
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Register.this,"Register Failed!" + e.toString(), Toast.LENGTH_SHORT).show();
                            load.setVisibility(View.GONE);
                            btRegister.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this,"Register Failed!" + error.toString(), Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.GONE);
                        btRegister.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
