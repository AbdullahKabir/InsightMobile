package com.example.abdullahkabir.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class loginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    TextView textView;
    EditText email;
    EditText password;
    Button button;
    private static final String DB_URL = "https://insightbd.000webhostapp.com/app/";
    private static final String USER = "root";
    private static final String PASS = "";

    private ProgressDialog progDailog;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
        progDailog.setCancelable(false);
        progDailog.dismiss();

        textView = (TextView) findViewById(R.id.needAcc);
        button = findViewById(R.id.LoginButton);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        progDailog.show();
        checkUser();
    }
});
        textView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(loginActivity.this,signupActivity.class);
        startActivity(intent);
    }
});
    }
    public void checkUser(){
            String Email =email.getText().toString();
            String Password = password.getText().toString();

        OkHttpClient client = NetworkConnectionManager.getClient();

        RequestBody body = new FormBody.Builder()
                .add("sub", "submit")
                .add("email", Email)
                .add("pass", Password)
                .build();
        Request request = new Request.Builder()
                .url(DB_URL + "loginByApp.php")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(loginActivity.this, "Connection timeout!", Toast.LENGTH_SHORT).show();
                        progDailog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String res = response.body().string();
                    Log.d(TAG, "onResponse: " + res);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (res.contains("Correct")) {
                                Intent intent = new Intent(loginActivity.this,profileActivity.class);
                                startActivity(intent);
                                progDailog.dismiss();
                            } else {
                                Toast.makeText(loginActivity.this, res, Toast.LENGTH_SHORT).show();
                                progDailog.dismiss();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(loginActivity.this, "Response unsuccessful!", Toast.LENGTH_SHORT).show();
                            progDailog.dismiss();
                        }
                    });
                }
            }
        });

    }
    }

