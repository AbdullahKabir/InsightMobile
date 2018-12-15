package com.example.abdullahkabir.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private static int TIME_OUT = 3000;
    AnimationSet animationSet = new AnimationSet(true);

    ImageView topLogo;
    Animation upDown;
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Making the top bar transparent
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_splash);
        //TopLogo Animation
        topLogo = findViewById(R.id.splashImage);
        upDown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        topLogo.setAnimation(upDown);

        //Top logo fade_out animation
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
        animation1.setDuration(1000);
        animation1.setStartOffset(3200);
        ImageView imageView = findViewById(R.id.splashImage);
        imageView.startAnimation(animation1);
        //splash screen handler

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, IndexActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }
}
