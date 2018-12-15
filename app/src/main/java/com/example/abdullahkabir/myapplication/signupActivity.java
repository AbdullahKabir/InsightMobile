package com.example.abdullahkabir.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class signupActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    EditText firstname, lastname, username, age, email, password, phonenumber, address;
    String server_url = "https://insightbd.000webhostapp.com/app/SignUp.php";
    AlertDialog.Builder builder;
    String res=null;

    private ProgressDialog progDailog;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        activity = this;
        progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
        progDailog.setCancelable(false);
        progDailog.dismiss();

        //Finding the views
        button = findViewById(R.id.signupButton);
        textView = findViewById(R.id.redirectToLogin);
        firstname = findViewById(R.id.signup_firstName);
        lastname = findViewById(R.id.signup_lastName);
        username = findViewById(R.id.signup_UserName);
        age = findViewById(R.id.signup_UserName);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        phonenumber = findViewById(R.id.signup_password);
        address = findViewById(R.id.signup_address);
        builder = new AlertDialog.Builder(signupActivity.this);
textView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(signupActivity.this,loginActivity.class);
        startActivity(intent);
    }
});
        //getting the values
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Firstname, Lastname, Username, Age, Email, Password, Phonenumber, Address;
                progDailog.show();

                Firstname = firstname.getText().toString();
                Lastname = lastname.getText().toString();
                Username = username.getText().toString();
                Age = age.getText().toString();
                Email = email.getText().toString();
                Password = password.getText().toString();
                Phonenumber = phonenumber.getText().toString();
                Address = address.getText().toString();
                if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)) {
                    Toast.makeText(signupActivity.this, "Required Fields are not given", Toast.LENGTH_SHORT).show();
                    progDailog.dismiss();
                } else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {


                                     if (response.contains("You have been registered")){
                                         Intent intent = new Intent(signupActivity.this, profileActivity.class);
                                         startActivity(intent);
                                         progDailog.dismiss();
                                     }
                                     else {
                                         Toast.makeText(signupActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                                         progDailog.dismiss();
                                     }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(signupActivity.this, "Error....", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                            progDailog.dismiss();

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("first_name", Firstname);
                            params.put("last_name", Lastname);
                            params.put("username", Username);
                            params.put("age", Age);
                            params.put("email", Email);
                            params.put("pass", Password);
                            params.put("phone", Phonenumber);
                            params.put("address", Address);
                            return params;
                        }
                    };

                    MySingleton.getmInstance(signupActivity.this).addToRequestqueue(stringRequest);
                }
            }
        });

            }
}
