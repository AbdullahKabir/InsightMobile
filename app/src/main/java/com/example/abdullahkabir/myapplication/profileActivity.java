package com.example.abdullahkabir.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class profileActivity extends AppCompatActivity {

     Button newsButton,webButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        newsButton=findViewById(R.id.button_newsfeed);
        webButton = findViewById(R.id.button_website);

        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profileActivity.this,newsfeedActivity.class);
                startActivity(intent);
            }
        });
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web = new Intent(profileActivity.this,websiteActivity.class);
                startActivity(web);
            }
        });

    }
}
