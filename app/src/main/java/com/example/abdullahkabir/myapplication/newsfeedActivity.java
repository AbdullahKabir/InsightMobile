package com.example.abdullahkabir.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class newsfeedActivity extends AppCompatActivity {

    private static final String TAG = "newsfeed";
    private static final String DB_URL = "https://insightbd.000webhostapp.com/app/";
    private static final String USER = "root";
    private static final String PASS = "";
    private ProgressDialog progDailog;
    Activity activity;

    final ArrayList<String> username = new ArrayList<String>();
    final ArrayList<String> date = new ArrayList<String>();
    final ArrayList<String> content = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        activity = this;
        progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
        progDailog.setCancelable(false);



        //Getting response
        OkHttpClient client = NetworkConnectionManager.getClient();
        Request request = new Request.Builder()
                .url(DB_URL + "post.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(newsfeedActivity.this, "co"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String res = response.body().string();
                    List a = decode_json(res);
                    for (int i=0; i<a.size(); i++){
                       username.add(decode_json(res).get(i).getUSER_NAME());
                       date.add(decode_json(res).get(i).getDATE());
                       content.add(decode_json(res).get(i).getCONTENT());
                    }

                    progDailog.show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListView listView = findViewById(R.id.postListview);
                            listAdapter listAdapter = new listAdapter();
                            listView.setAdapter(listAdapter);
                            progDailog.dismiss();
                        }
                    });


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(newsfeedActivity.this, "Response unsuccessful!", Toast.LENGTH_SHORT).show();
                            progDailog.dismiss();
                        }
                    });
                }
            }
        });

    }
    private List<postModel> decode_json(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<postModel>>(){}.getType();
        List<postModel> postModels = gson.fromJson(json,listType);
        return postModels;
//        Log.d("newsfeedActivity","List:"+ postModels.size());

    }
    class listAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return username.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.newsfeedlayout,null);
            TextView textView_authorname= convertView.findViewById(R.id.textView_username);
            TextView textView_date= convertView.findViewById(R.id.textView_date);
            TextView textView_content= convertView.findViewById(R.id.textView_content);

            textView_authorname.setText(username.get(position));

            textView_date.setText(date.get(position));

            textView_content.setText(content.get(position));

            return convertView;
        }
    }
}
