package com.example.ticket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {
    SharedPreferences preferences;
    ImageView imageView;
    TextView title,descr, price;
    Button book;
    String seat;
    Spinner grp;
    String username, userid, movie_id;
    private static String URL_REGIST = "http://bcb4937.000webhostapp.com/ticket/android_add_book.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        imageView = findViewById(R.id.imageDetail);
        title = findViewById(R.id.mtitle);
        descr = findViewById(R.id.mdesc);
        price = findViewById(R.id.mprice);
        grp = findViewById(R.id.spinner);
        book = findViewById(R.id.bookBtn);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.group, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grp.setAdapter(adapter);

        preferences = getSharedPreferences("myPrefs",MODE_PRIVATE);
        String name = preferences.getString("title","");
        String desc = preferences.getString("desc","");
        String img = preferences.getString("img","");
        movie_id = preferences.getString("id","");
        final String prc = preferences.getString("price","");

        username = preferences.getString("user_name","");
        userid = preferences.getString("user_id","");

        Log.i("details: ",username+" "+userid+" "+movie_id);
        Glide.with(this).load(img).into(imageView);
        title.setText(name);
        descr.setText(desc);
        String p = "₹ "+prc;
        price.setText(p);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat = grp.getSelectedItem().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putString("movieid_pay",movie_id);
                editor.putString("userid_pay",userid);
                editor.putString("price_pay",prc);
                editor.putString("seat_pay",seat);
                editor.apply();
                Booking();
            }
        });
    }
    private void Booking(){
        seat = grp.getSelectedItem().toString();
        final String id = this.movie_id;
        final String userid = this.userid;
        final String seat = this.seat;
        Log.i("details",id+" "+userid+" "+seat);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(BookingActivity.this,"Booking Success! Continue to payment", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(BookingActivity.this,PaymentActivity.class));
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(BookingActivity.this,"Booking Failed!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookingActivity.this,"Booking Failed!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("movie_id",id);
                params.put("user_id",userid);
                params.put("seat",seat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
