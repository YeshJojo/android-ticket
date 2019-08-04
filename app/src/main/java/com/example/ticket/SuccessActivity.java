package com.example.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lal.adhish.gifprogressbar.GifView;

public class SuccessActivity extends AppCompatActivity {
    Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        home = findViewById(R.id.backtohome);
        GifView gifView = findViewById(R.id.progressBar);
        gifView.setImageResource(R.drawable.check);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessActivity.this,HomeActivity.class));
            }
        });
    }
}
