package com.example.ticket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class PaymentActivity extends AppCompatActivity {
    LinearLayout gpay, apay, cpay;
    CardView gcard, acard, ccard;
    String upi, userid, movieid, price, seat;
    SharedPreferences preferences;
    TextView tv,total;
    int tot;
    private static String URL_REGIST = "http://bcb4937.000webhostapp.com/ticket/android_payment.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        total = findViewById(R.id.totalDis);

        gcard = findViewById(R.id.gpayCard);
        acard = findViewById(R.id.amazonPay);
        ccard = findViewById(R.id.cardPay);

        gpay = findViewById(R.id.gpayDetails);
        apay = findViewById(R.id.apayDetails);
        cpay = findViewById(R.id.cardDetails);

        gpay.setVisibility(View.GONE);
        apay.setVisibility(View.GONE);
        cpay.setVisibility(View.GONE);

        preferences = getSharedPreferences("myPrefs",MODE_PRIVATE);
        userid = preferences.getString("userid_pay","");
        movieid = preferences.getString("movieid_pay","");
        price = preferences.getString("price_pay","");
        seat = preferences.getString("seat_pay","");
        int prc = Integer.valueOf(price);
        int sea = Integer.valueOf(seat);
        tot = calc(prc,sea);
        total.setText("Total Amount to be paid: "+tot+" ("+seat+" Seats)");

        gcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpay.setVisibility(View.VISIBLE);
                apay.setVisibility(View.GONE);
                cpay.setVisibility(View.GONE);
                tv = findViewById(R.id.gupi);
                Button pay = findViewById(R.id.gpay);
                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upi = tv.getText().toString();
                        Payment();
                    }
                });

            }
        });
        acard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apay.setVisibility(View.VISIBLE);
                gpay.setVisibility(View.GONE);
                cpay.setVisibility(View.GONE);
                final TextView tv = findViewById(R.id.aupi);
                Button pay = findViewById(R.id.apay);
                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upi = tv.getText().toString();
                        Payment();
                    }
                });
            }
        });
        ccard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cpay.setVisibility(View.VISIBLE);
                gpay.setVisibility(View.GONE);
                apay.setVisibility(View.GONE);
                final TextView cno = findViewById(R.id.cardno);
                Button pay = findViewById(R.id.cardPayBtn);
                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upi = cno.getText().toString();
                        preferences = getSharedPreferences("myPrefs",MODE_PRIVATE);
                        userid = preferences.getString("userid_pay","");
                        movieid = preferences.getString("movieid_pay","");
                        PaymentCard();
                    }
                });
            }
        });
    }
    private void Payment(){
        final String upi = this.upi;
        final String userid = this.userid;
        final String movieid = this.movieid;
        final int total = this.tot;
        Log.i("details",userid+" "+movieid+" "+upi);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(PaymentActivity.this,"Paymnet Success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PaymentActivity.this,SuccessActivity.class));
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(PaymentActivity.this,"Paymnet Failed!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,"Paymnet Failed!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("book_id",movieid);
                params.put("user_id",userid);
                params.put("type","UPI");
                params.put("status",upi);
                params.put("total",Integer.toString(total));
                Log.i("params", Integer.toString(total));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void PaymentCard(){
        final String upi = this.upi;
        final String userid = this.userid;
        final String movieid = this.movieid;
        Log.i("details",userid+" "+movieid+" "+upi);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(PaymentActivity.this,"Paymnet Success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PaymentActivity.this,SuccessActivity.class));
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(PaymentActivity.this,"Paymnet Failed!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentActivity.this,"Paymnet Failed!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("book_id",movieid);
                params.put("user_id",userid);
                params.put("type","Card");
                params.put("status",upi);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    int calc(int seat, int amt){
        int tot = seat*amt;
        return tot;
    }
}
